package com.usareboot.back.dto.interfaces.sciense;

import java.math.BigInteger;

public interface ScienceShedulesResponse {
    BigInteger getscience_dissertation_schedule_id();
    BigInteger getscience_dissertation_id();
    BigInteger getscience_schedule_type_id();
    String getschedule_type_code();
    String getschedule_type_name();
    String getdate_plan();
    String getdate_fact();
}
