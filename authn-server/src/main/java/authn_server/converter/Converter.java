package authn_server.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/* This class is used to convert a request into actual object data and actual object data into a response*/
@Component
public class Converter {

    public Object convert(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
