package com.primitive.library.helper.varidate;

import com.primitive.library.exception.ObjectSettingException;
import com.primitive.library.helper.varidate.exception.VaridatorException;

public interface Varidator {
	public boolean compare()
			throws VaridatorException, ObjectSettingException;
}
