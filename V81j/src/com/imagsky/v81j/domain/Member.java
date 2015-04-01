package com.imagsky.v81j.domain;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import com.imagsky.util.CommonUtil;
import com.imagsky.v81j.domain.App;

import java.util.*;

@Entity
@Table(name = "tb_member")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "SYS_GUID", referencedColumnName = "SYS_GUID")
public class Member extends SysObject {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final Integer FULLNAME_DISP_FIRSTLAST = 0;
    public static final Integer FULLNAME_DISP_FIRSTLAST_COMMA = 1;
    public static final Integer FULLNAME_DISP_LASTFIRST = 2;
    public static final Integer FULLNAME_DISP_LASTFIRST_COMMA = 3;

    public static String getFullName(Member thisMember) {
        if (thisMember == null) {
            return null;
        }
        if (thisMember.getMem_fullname_display_type() == FULLNAME_DISP_FIRSTLAST) {
            return thisMember.getMem_firstname() + " " + thisMember.getMem_lastname();
        } else if (thisMember.getMem_fullname_display_type() == FULLNAME_DISP_FIRSTLAST_COMMA) {
            return thisMember.getMem_firstname() + ", " + thisMember.getMem_lastname();
        } else if (thisMember.getMem_fullname_display_type() == FULLNAME_DISP_LASTFIRST) {
            return thisMember.getMem_lastname() + " " + thisMember.getMem_firstname();
        } else if (thisMember.getMem_fullname_display_type() == FULLNAME_DISP_LASTFIRST_COMMA) {
            return thisMember.getMem_lastname() + ", " + thisMember.getMem_firstname();
        } else {
            return thisMember.getMem_firstname() + " " + thisMember.getMem_lastname();
        }
    }
    @Column(length = 50)
    private String mem_login_email;
    @Column(length = 50)
    private String mem_passwd;
    @Column(length = 70)
    private String mem_firstname;
    @Column(length = 70)
    private String mem_lastname;
    @Column(length = 25)
    private String mem_shopurl;
    @Column(length = 50)
    private String mem_shopname;
    @Temporal(TemporalType.TIMESTAMP)
    private Date mem_lastlogindate;
    @Column
    private Integer mem_salutation;
    @Column(length = 80)
    private String mem_shopbanner;
    @Column(length = 50)
    private String mem_shop_hp_arti;
    @Column
    private Integer mem_max_sellitem_count;
    @Column
    private Integer mem_fullname_display_type;
    @Column(length = 30)
    private String mem_display_name;
    @Column
    private Integer mem_feedback;
    @Column
    private Double mem_cash_balance;
    @Column
    private Boolean fb_mail_verified;
    @Column
    private String fb_id;
    @Column
    private Integer mem_meatpoint;
    @Column
    private String package_type;
    
    @ManyToMany
    @JoinTable(name="tb8_app_user_xref",
			joinColumns = { @JoinColumn(name="MEMBER_GUID") },
			inverseJoinColumns = { @JoinColumn(name="APP_GUID") } )
    private Set<App> apps;

    /***
    @ManyToMany
    @JoinTable(name = "tb_member_service_xref",
    joinColumns = {
        @JoinColumn(name = "MEM_SYS_GUID")},
    inverseJoinColumns = {
        @JoinColumn(name = "SERVICE_ID")})
    private Collection<Service> services;
    ***/

    public String getMem_shopbanner() {
        return mem_shopbanner;
    }

    public void setMem_shopbanner(String memShopbanner) {
        mem_shopbanner = memShopbanner;
    }

    public String getMem_shop_hp_arti() {
        return mem_shop_hp_arti;
    }

    public void setMem_shop_hp_arti(String memShopHpArti) {
        mem_shop_hp_arti = memShopHpArti;
    }

    public String getMem_login_email() {
        return mem_login_email;
    }

    public void setMem_login_email(String memLoginEmail) {
        mem_login_email = memLoginEmail;
    }

    public String getMem_passwd() {
        return mem_passwd;
    }

    public void setMem_passwd(String memPasswd) {
        mem_passwd = memPasswd;
    }

    public String getMem_firstname() {
        return mem_firstname;
    }

    public void setMem_firstname(String memFirstname) {
        mem_firstname = memFirstname;
    }

    public String getMem_lastname() {
        return mem_lastname;
    }

    public void setMem_lastname(String memLastname) {
        mem_lastname = memLastname;
    }

    public String getMem_shopurl() {
        return mem_shopurl;
    }

    public void setMem_shopurl(String memShopurl) {
        mem_shopurl = memShopurl;
    }

    public String getMem_shopname() {
        return mem_shopname;
    }

    public void setMem_shopname(String memShopname) {
        mem_shopname = memShopname;
    }

    public Date getMem_lastlogindate() {
        return mem_lastlogindate;
    }

    public void setMem_lastlogindate(Date memLastlogindate) {
        mem_lastlogindate = memLastlogindate;
    }

    public Integer getMem_salutation() {
        return mem_salutation;
    }

    public void setMem_salutation(Integer memSalutation) {
        mem_salutation = memSalutation;
    }

    public Integer getMem_max_sellitem_count() {
        return mem_max_sellitem_count;
    }

    public void setMem_max_sellitem_count(Integer memMaxSellitemCount) {
        mem_max_sellitem_count = memMaxSellitemCount;
    }

    public Integer getMem_fullname_display_type() {
        return mem_fullname_display_type;
    }

    public void setMem_fullname_display_type(Integer memFullnameDisplayType) {
        mem_fullname_display_type = memFullnameDisplayType;
    }

    public String getMem_display_name() {
        return mem_display_name;
    }

    public void setMem_display_name(String memDisplayName) {
        mem_display_name = memDisplayName;
    }

    public String getNickName() {
        if (CommonUtil.isNullOrEmpty(mem_display_name)) {
            return CommonUtil.stringTokenize(mem_login_email, "@")[0];
        } else {
            return mem_display_name;
        }
    }

    public Integer getMem_meatpoint() {
        return mem_meatpoint;
    }

    public void setMem_meatpoint(Integer memMeatpoint) {
        mem_meatpoint = memMeatpoint;
    }

    public Integer getMem_feedback() {
        return mem_feedback;
    }

    public void setMem_feedback(Integer memFeedback) {
        mem_feedback = memFeedback;
    }

    /*** V6 
    public Collection<Service> getServices() {
        return services;
    }

    public void setRoles(Collection<Service> services) {
        this.services = services;
    } ***/

    public Double getMem_cash_balance() {
        return mem_cash_balance;
    }

    public void setMem_cash_balance(Double memCashBalance) {
        mem_cash_balance = memCashBalance;
    }

    public Boolean getFb_mail_verified() {
        return fb_mail_verified;
    }

    public void setFb_mail_verified(Boolean fbMailVerified) {
        fb_mail_verified = fbMailVerified;
    }

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fbId) {
        fb_id = fbId;
    }
    
    public String getPackage_type() {
		return package_type;
	}

	public void setPackage_type(String package_type) {
		this.package_type = package_type;
	}

	public Set<App> getApps() {
		return apps;
	}
	
	public void setApps(Set<App> apps) {
		this.apps = apps;
	}

	public static TreeMap<String, Object> getFields(Member obj) {
        TreeMap<String, Object> aHt = new TreeMap<String, Object>();
        aHt.put("mem_login_email", obj.mem_login_email);
        aHt.put("mem_passwd", obj.mem_passwd);
        aHt.put("mem_firstname", obj.mem_firstname);
        aHt.put("mem_lastname", obj.mem_lastname);
        aHt.put("mem_shopurl", obj.mem_shopurl);
        aHt.put("mem_shopname", obj.mem_shopname);
        aHt.put("mem_lastlogindate", obj.mem_lastlogindate);
        aHt.put("mem_salutation", obj.mem_salutation);
        aHt.put("mem_shopbanner", obj.mem_shopbanner);
        aHt.put("mem_max_sellitem_count", obj.mem_max_sellitem_count);
        aHt.put("mem_display_name", obj.getMem_display_name());
        aHt.put("mem_fullname_display_type", obj.getMem_fullname_display_type());
        aHt.put("mem_feedback", obj.getMem_feedback());
        aHt.put("mem_cash_balance", obj.getMem_cash_balance());
        aHt.put("fb_id", obj.getFb_id());
        aHt.put("fb_mail_verified", obj.getFb_mail_verified());
        aHt.put("package_type",obj.getPackage_type());
        ///aHt.put("mem_address",obj.mem_address);
        aHt.putAll(SysObject.getSysFields(obj));
        return aHt;
    }

    public static List getWildFields() {
        List returnList = new ArrayList();
//		returnList.add("mem_shopname");
        return returnList;
    }
}
