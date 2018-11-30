package com.gordeev.movieland.controller.util;

import com.gordeev.movieland.entity.Currency;

import java.beans.PropertyEditorSupport;

public class CurrencyPropertyEditor extends PropertyEditorSupport {
    public void setAsText(final String text) throws IllegalArgumentException {
        setValue(Currency.getByName(text));
    }
}
