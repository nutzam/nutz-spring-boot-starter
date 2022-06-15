package org.nutz.spring.boot.hanlder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

import org.nutz.castor.Castors;
import org.nutz.json.JsonFormat;
import org.nutz.json.JsonRender;
import org.nutz.json.JsonTypeHandler;
import org.nutz.lang.Mirror;

/**
 * @author wkipy
 *
 */
public class JsonLocalDateLikeHandler extends JsonTypeHandler {

    /**
     * @param mirror
     * @param obj
     * @return
     * @see org.nutz.json.JsonTypeHandler#supportFromJson(org.nutz.lang.Mirror,
     *      java.lang.Object)
     */
    @Override
    public boolean supportFromJson(Mirror<?> mirror, Object obj) {
        return mirror.isLocalDateTimeLike();
    }

    @Override
    public boolean supportToJson(Mirror<?> mirror, Object obj, JsonFormat jf) {
        return mirror.isLocalDateTimeLike();
    }

    @Override
    public void toJson(Mirror<?> mirror, Object currentObj, JsonRender r, JsonFormat jf) throws IOException {
        String df = jf.getDateFormatRaw();
        if (df == null)
            df = "yyyy-MM-dd HH:mm:ss.SSS";
        if (mirror.getType().equals(LocalDate.class)) {
            df = "yyyy-MM-dd";
        }
        Locale locale = null;
        String tmp = jf.getLocale();
        if (tmp != null)
            locale = Locale.forLanguageTag(tmp);
        else
            locale = Locale.getDefault();
        r.string2Json(DateTimeFormatter.ofPattern(df, locale).withZone(ZoneId.systemDefault()).format((TemporalAccessor) currentObj));
    }

    @Override
    public Object fromJson(Object obj, Mirror<?> mirror) throws Exception {
        return Castors.me().castTo(obj, mirror.getType());
    }
}
