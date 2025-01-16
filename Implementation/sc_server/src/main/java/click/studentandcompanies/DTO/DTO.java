package click.studentandcompanies.DTO;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DTO implements Serializable {
    private final Map<String, Object> properties = new HashMap<>();

    public void addProperty(String key, Object value) {
        properties.put(key, value);
    }

    public Object getProperty(String key) {
        return properties.get(key);
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName()).append(" {");
        properties.forEach((key, value) -> sb.append(key).append("=").append(value).append(", "));
        if (!properties.isEmpty()) {
            // Rimuove l'ultima virgola e spazio
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DTO that = (DTO) o;
        return Objects.equals(properties, that.properties);
    }
}
