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
    public String toString() {
        return "太史慈";
    }
}
