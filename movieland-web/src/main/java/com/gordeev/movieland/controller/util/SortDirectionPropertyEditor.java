package com.gordeev.movieland.controller.util;

import com.gordeev.movieland.entity.SortDirection;

import java.beans.PropertyEditorSupport;

public class SortDirectionPropertyEditor extends PropertyEditorSupport {

    public void setAsText(final String text) throws IllegalArgumentException {
        setValue(SortDirection.getByName(text));
    }

}
