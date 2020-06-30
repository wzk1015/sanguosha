package sanguosha.people.fire;

import sanguosha.cards.Card;
import sanguosha.cards.basic.Sha;
import sanguosha.manager.GameManager;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

public class TaiShiCi extends Person {
    private boolean isNB;

    public TaiShiCi() {
        super(4, Nation.WU);
    }

    @Skill("天义")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("天义") && hasNotUsedSkill1()) {
            Person p;
            do {
                printlnToIO("choose a person with hand cards to 拼点");
                p = selectPlayer();
            } while (p.getCards().isEmpty());
            if (GameManager.pinDian(this,p)) {
                isNB = true;
                setMaxShaCount(2);
            } else {
                setMaxShaCount(0);
            }
            setHasUsedSkill1(true);
        }
        return false;
    }

    @Override
    public boolean useCard(Card card) {
        boolean ret = super.useCard(card);
        if (card instanceof Sha && isNB) {
            Sha sha = new Sha(card.color(), card.number());
            sha.setTaken(true);
            sha.setThisCard(card);
            if (!sha.askTarget(this)) {
                return ret;
            }
            useCard(sha);
        }
        return ret;
    }

    @Override
    public int getShaDistance() {
        if (isNB) {
            return 10000;
        }
        return super.getShaDistance();
    }

    @Override
    public void endPhase() {
        setMaxShaCount(1);
        isNB = false;
    }

    @Override
    public String name() {
        return "太史慈";
    }

    @Override
    public String skillsDescription() {
        return "天义：出牌阶段限一次，你可以与一名角色拼点：若你赢，本回合你可以多使用一张【杀】、" +
                "使用【杀】无距离限制且可以多选择一个目标；若你没赢，本回合你不能使用【杀】。";
    }
}
