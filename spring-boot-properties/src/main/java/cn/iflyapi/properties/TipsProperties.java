package cn.iflyapi.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
//import org.hibernate.validator.constraints.Length;
import java.util.List;

/**
 * Author: qfwang
 * Date: 2017-12-07 下午2:04
 */
@Component
@ConfigurationProperties(prefix = "tips")
public class TipsProperties {

  //  @NotBank
    private int code;
    private String msg;
    private String result;
    private Boolean enabled;
    private List<String> list;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
