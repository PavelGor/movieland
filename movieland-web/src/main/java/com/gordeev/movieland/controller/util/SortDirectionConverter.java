package com.gordeev.movieland.controller.util;

import com.gordeev.movieland.vo.SortDirection;

import java.beans.PropertyEditorSupport;

public class SortDirectionConverter  extends PropertyEditorSupport {

    public void setAsText(final String text) throws IllegalArgumentException {
        setValue(SortDirection.getByName(text));
    }

}