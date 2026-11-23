package com.andy.taskmanager.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TaskPatchRequest(
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    /*
     * Regex: "(?s).*\\S.*"
     *
     * Purpose: Validates that the input contains at least one non-whitespace character.
     * (i.e., Rejects null, empty strings, or strings with only spaces/newlines)
     *
     * Breakdown:
     * - (?s)  : DOTALL mode. Allows '.' to match newline characters (\n).
     * - .*    : Matches any sequence of characters.
     * - \\S   : Matches exactly one non-whitespace character (The core check).
     * - .*    : Matches any trailing characters.
     *
     * Business meaning: The field is mandatory and cannot be purely blank spaces.
     */
    @Pattern(regexp = "(?s).*\\S.*", message = "Title must not be blank")
    String title,

    @Size(max = 500, message = "Description can be up to 500 characters")
    String description,

    Boolean completed,
    Long categoryId
) {
}
