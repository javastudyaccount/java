package jp.btsol.mahjong.web.fw.handler;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.btsol.mahjong.entity.ErrorDataEntity;

/**
 * コントローラ全体でのExceptionハンドラー
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MahjongExceptionHandler {
    /**
     * ObjectMapper
     */
    @Autowired
    private ObjectMapper om;

    /**
     * Exceptionハンドラー
     * 
     * @param request            WebRequest
     * @param redirectAttributes RedirectAttributes
     * @param ex                 Exception
     * @return String
     */
    @ExceptionHandler(HttpStatusCodeException.class)
    public ModelAndView handleHttpStatusCodeException(WebRequest request, RedirectAttributes redirectAttributes,
            HttpStatusCodeException ex) {
        try {
            ErrorDataEntity error = om.readValue(ex.getResponseBodyAsString(), ErrorDataEntity.class);
            redirectAttributes.addFlashAttribute("apierror", error);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("systemError", e.getLocalizedMessage());
        }
        Class formClazz = (Class) ((ServletWebRequest) request).getRequest().getSession().getAttribute("formClazz");
        if (!formClazz.isPrimitive()) {
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

        String viewName = (String) ((ServletWebRequest) request).getRequest().getSession().getAttribute("viewName");
        ModelAndView mav = new ModelAndView("redirect:" + viewName);
        return mav;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected String handleMethodArgumentNotValid(RedirectAttributes redirectAttributes, HttpServerErrorException ex) {
        return "redirect:/system-error";
    }
}
