package com.sakuya.hangup.entity;

public class SkillEntity {
    private int skill_id;
    private String skill_name;
    private String skill_ctx;
    private int skill_lv;
    private String skill_pz;
    private String skill_type;
    private boolean isDelete;
    private String attr;

    public int getSkill_id() {
        return skill_id;
    }

    public void setSkill_id(int skill_id) {
        this.skill_id = skill_id;
    }

    public String getSkill_name() {
        return skill_name;
    }

    public void setSkill_name(String skill_name) {
        this.skill_name = skill_name;
    }

    public String getSkill_ctx() {
        return skill_ctx;
    }

    public void setSkill_ctx(String skill_ctx) {
        this.skill_ctx = skill_ctx;
    }

    public int getSkill_lv() {
        return skill_lv;
    }

    public void setSkill_lv(int skill_lv) {
        this.skill_lv = skill_lv;
    }

    public String getSkill_pz() {
        return skill_pz;
    }

    public void setSkill_pz(String skill_pz) {
        this.skill_pz = skill_pz;
    }

    public String getSkill_type() {
        return skill_type;
    }

    public void setSkill_type(String skill_type) {
        this.skill_type = skill_type;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public SkillEntity(int skill_id, String skill_name, String skill_ctx, int skill_lv, String skill_pz, String skill_type, boolean isDelete) {
        this.skill_id = skill_id;
        this.skill_name = skill_name;
        this.skill_ctx = skill_ctx;
        this.skill_lv = skill_lv;
        this.skill_pz = skill_pz;
        this.skill_type = skill_type;
        this.isDelete = isDelete;
    }
}
