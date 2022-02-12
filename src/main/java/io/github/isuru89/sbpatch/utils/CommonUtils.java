package io.github.isuru89.sbpatch.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchOperation;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public final class CommonUtils {

    private static final TypeReference<HashMap<String,Object>> MAP_TYPE_REFERENCE = new TypeReference<HashMap<String,Object>>() {};
    private static final List<String> PATH_FIELD_NAMES = Arrays.asList("path", "from", "to");

    public static List<String> extractPathsFromPatchOperations(List<JsonPatchOperation> operations, ObjectMapper deserializer) {
        return operations.stream().flatMap(op -> {
            try {
                Map<String, Object> map = deserializer.readValue(deserializer.writeValueAsString(op), MAP_TYPE_REFERENCE);
                return PATH_FIELD_NAMES.stream().map(field -> {
                    if (map.containsKey(field)) {
                        return (String) map.get(field);
                    }
                    return null;
                }).filter(Objects::nonNull);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Invalid set of patch operations!");
            }
        }).collect(Collectors.toList());
    }

    /**
     * This method will return true if at least one entry in <code>checkList</code> matches with an entry
     * in <code>targetList</code>. Comparison will be done using prefix match.
     *
     * @param targetList target list to compare against.
     * @param checkList check list items.
     * @return true if at least one matched, otherwise false.
     */
    public static boolean isAnyMatch(List<String> targetList, List<String> checkList) {
        if (checkList == null || checkList.isEmpty()) {
            return false;
        }

        for (String chk : checkList) {
            if (targetList.stream().anyMatch(ts -> StringUtils.startsWithIgnoreCase(ts, chk))) {
                return true;
            }
        }
        return false;
    }

}
