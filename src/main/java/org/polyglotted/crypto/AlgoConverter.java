package org.polyglotted.crypto;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

public class AlgoConverter implements IStringConverter<Algo> {
    @Override
    public Algo convert(String value) {
        try {
            return Algo.valueOf(value);
        }
        catch (Exception ex) {
            throw new ParameterException("illegal algorithm name");
        }
    }
}