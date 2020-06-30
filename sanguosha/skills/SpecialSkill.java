package sanguosha.skills;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Skill("特殊技")
public @interface SpecialSkill {
    String value();
}
