package sanguosha.skills;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@ForcesSkill("觉醒技")
@RestrictedSkill("觉醒技")
public @interface WakeUpSkill {
    String value();
}
