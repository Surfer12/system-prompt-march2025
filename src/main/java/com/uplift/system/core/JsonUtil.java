import com.uplift.system.interop.adapters.JavaEntity;
import com.uplift.system.interop.adapters.PythonEntity;
import com.uplift.system.interop.adapters.GoEntityAdapter;
import com.uplift.system.interop.adapters.SwiftEntityAdapter;
import com.uplift.system.interop.adapters.MojoEntityAdapter;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T fromJson(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }
}