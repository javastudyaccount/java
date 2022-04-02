package jp.btsol.mahjong.web.fw;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.error.ErrorAttributeOptions.Include;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * アプリケーション全体のエラーコントローラー //
 * https://qiita.com/niwasawa/items/f3479ef16efa488039fb
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
@Slf4j
public class MahjongErrorController implements ErrorController {
    /**
     * エラーページのパス。
     */
    @Value("${server.error.path:${error.path:/error}}")
    private String errorPath;

    /**
     * エラーページのパスを返す。
     *
     * @return エラーページのパス
     */
//    @Override
    public String getErrorPath() {
        return errorPath;
    }

    /**
     * HTML レスポンス用の ModelAndView オブジェクトを返す。
     *
     * @param request            リクエスト情報
     * @param redirectAttributes RedirectAttributes
     * @return HTML レスポンス用の ModelAndView オブジェクト
     */
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView myErrorHtml(WebRequest request, RedirectAttributes redirectAttributes) {

        // HTTP ステータスを決める
        // ここでは 404 以外は全部 500 にする
        Object statusCode = ((ServletWebRequest) request).getRequest()
                .getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        log.info("status code: {}", statusCode);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (statusCode != null && statusCode.toString().equals("404")) {
            status = HttpStatus.NOT_FOUND;
            ModelAndView mav = new ModelAndView();
            Map<String, Object> errors = MahjongErrorController.getErrorAttributes(request);
            mav.setViewName("404"); // 404.html
            mav.addAllObjects(errors);
            return mav;
        }

        Map<String, Object> bindingErrors = MahjongErrorController.getBindingErrorAttributes(request);
        if (bindingErrors.containsKey("errors")) {
            redirectAttributes.addFlashAttribute("errors", bindingErrors.get("errors"));
        }
        Class formClazz = (Class) ((ServletWebRequest) request).getRequest().getSession().getAttribute("formClazz");
        if (Objects.nonNull(formClazz)) {
            try {
                Object form = formClazz.getDeclaredConstructor().newInstance();
                Map<String, String[]> params = ((ServletWebRequest) request).getRequest().getParameterMap();
                Map<String, String> result = params.entrySet().stream()
                        .collect(Collectors.toMap(Entry::getKey, e -> e.getValue()[0]));
                BeanUtils.populate(form, result);
                String formName = StringUtils.unCapitalize(formClazz.getSimpleName());
                redirectAttributes.addFlashAttribute(formName, form);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
        }
        Object viewName = ((ServletWebRequest) request).getRequest().getSession().getAttribute("viewName");
        if (Objects.isNull(viewName)) {
            viewName = "/login";
        }
        ModelAndView mav = new ModelAndView("redirect:" + String.valueOf(viewName));
        return mav;
    }

    /**
     * JSON レスポンス用の ResponseEntity オブジェクトを返す。
     *
     * @param request リクエスト情報
     * @return JSON レスポンス用の ResponseEntity オブジェクト
     */
    @RequestMapping
    public ResponseEntity<Map<String, Object>> myErrorJson(HttpServletRequest request) {

        // HTTP ステータスを決める
        // ここでは 404 以外は全部 500 にする
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (statusCode != null && statusCode.toString().equals("404")) {
            status = HttpStatus.NOT_FOUND;
        }

        // 出力したい情報をセットする
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("path", request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));

        return new ResponseEntity<>(body, status);
    }

    /**
     * エラー情報を抽出する。
     *
     * @param req リクエスト情報
     * @return エラー情報
     */
    private static Map<String, Object> getErrorAttributes(WebRequest req) {
        // DefaultErrorAttributes クラスで詳細なエラー情報を取得する
        DefaultErrorAttributes dea = new DefaultErrorAttributes();
        return dea.getErrorAttributes(req, ErrorAttributeOptions.defaults());
    }

    /**
     * bindingエラー情報を抽出する。
     *
     * @param req リクエスト情報
     * @return bindingエラー情報
     */
    private static Map<String, Object> getBindingErrorAttributes(WebRequest req) {
        // DefaultErrorAttributes クラスで詳細なエラー情報を取得する
        DefaultErrorAttributes dea = new DefaultErrorAttributes();
        return dea.getErrorAttributes(req, ErrorAttributeOptions.of(Include.BINDING_ERRORS));
    }

    /**
     * レスポンス用の HTTP ステータスを決める。
     *
     * @param req リクエスト情報
     * @return レスポンス用 HTTP ステータス
     */
    private static HttpStatus getHttpStatus(HttpServletRequest req) {
        // HTTP ステータスを決める
        // ここでは 404 以外は全部 500 にする
        Object statusCode = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (statusCode != null && statusCode.toString().equals("404")) {
            status = HttpStatus.NOT_FOUND;
        }
        return status;
    }
}
