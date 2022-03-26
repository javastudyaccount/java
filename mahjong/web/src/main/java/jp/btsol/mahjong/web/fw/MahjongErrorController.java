package jp.btsol.mahjong.web.fw;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * アプリケーション全体のエラーコントローラー //
 * https://qiita.com/niwasawa/items/f3479ef16efa488039fb
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
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
     * @param request リクエスト情報
     * @return HTML レスポンス用の ModelAndView オブジェクト
     */
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView myErrorHtml(HttpServletRequest request) {

        // HTTP ステータスを決める
        // ここでは 404 以外は全部 500 にする
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (statusCode != null && statusCode.toString().equals("404")) {
            status = HttpStatus.NOT_FOUND;
        }

        // 出力したい情報をセットする
        ModelAndView mav = new ModelAndView();
        mav.setStatus(status); // HTTP ステータスをセットする
        mav.setViewName("404"); // 404.html
        mav.addObject("myErrorMessage", "Page not found.");
        mav.addObject("timestamp", new Date());
        mav.addObject("status", status.value());
        mav.addObject("path", request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));

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
}
