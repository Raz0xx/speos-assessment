package com.devaleriola.speos_assessment.entities.test;

import com.devaleriola.speos_assessment.entities.GenericDtoImpl;

public class Test extends GenericDtoImpl implements TestBiz {

    private String text;

    @Override
    public boolean validateText(String text) {
        //verification logic
        return true;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }
}
