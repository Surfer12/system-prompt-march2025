package com.uplift.system.interop.adapters;

import com.uplift.system.interop.InteroperabilityManager.LanguageAdapter;
import java.util.HashMap;
import java.util.Map;

public class MojoEntityAdapter implements LanguageAdapter<MojoEntity> {

    @Override
    public Map<String, Object> toNormalizedForm(MojoEntity entity) {
        Map<String, Object> normalized = new HashMap<>();
        normalized.put("id", entity.getId());
        normalized.put("name", entity.getName());
        normalized.put("data", entity.getData());
        return normalized;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> R fromNormalizedForm(Map<String, Object> normalized) {
        MojoEntity entity = new MojoEntity();
        entity.setId((Integer) normalized.get("id"));
        entity.setName((String) normalized.get("name"));
        entity.setData((Map<String, Object>) normalized.get("data"));
        return (R) entity;
    }

    @Override
    public boolean validateEntity(MojoEntity entity) {
        return entity != null && entity.getName() != null && !entity.getName().isEmpty();
    }

    @Override
    public Map<String, Object> getCapabilities() {
        Map<String, Object> capabilities = new HashMap<>();
        capabilities.put("language", "mojo");
        capabilities.put("supportsVectorization", true);
        return capabilities;
    }
}