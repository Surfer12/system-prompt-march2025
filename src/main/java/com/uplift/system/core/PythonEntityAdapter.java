
package com.uplift.system.interop.adapters;

import com.uplift.system.interop.InteroperabilityManager.LanguageAdapter;
import java.util.HashMap;
import java.util.Map;

public class PythonEntityAdapter implements LanguageAdapter<PythonEntity> {
    @Override
    public Map<String, Object> toNormalizedForm(PythonEntity entity) {
        Map<String, Object> normalized = new HashMap<>();
        normalized.put("id", entity.getId());
        normalized.put("name", entity.getName());
        normalized.put("data", entity.getData());
        return normalized;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <R> R fromNormalizedForm(Map<String, Object> normalized) {
        PythonEntity entity = new PythonEntity();
        entity.setId(((Number)normalized.get("id")).longValue());
        entity.setName((String)normalized.get("name"));
        entity.setData((Map<String, Object>)normalized.get("data"));
        return (R)entity;
    }
    
    @Override
    public boolean validateEntity(PythonEntity entity) {
        return entity != null && entity.getName() != null && !entity.getName().isEmpty();
    }
    
    @Override
    public Map<String, Object> getCapabilities() {
        Map<String, Object> capabilities = new HashMap<>();
        capabilities.put("language", "python");
        capabilities.put("supportsDynamicTyping", true);
        return capabilities;
    }
}