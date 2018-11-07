/*    */ package bupt.intt.wsmonitor.servicemonitor.domain;
/*    */ 
/*    */ public class Person
/*    */ {
/*    */   private String cn;
/*    */   private String sn;
/*    */   private String description;
/*    */   private String givenname;
/*    */   private String mail;
/*    */   private String manager;
/*    */   private String uid;
/*    */   private String userpassword;
/*    */ 
/*    */   public void setCn(String cn)
/*    */   {
/* 13 */     this.cn = cn;
/*    */   }
/*    */   public String getCn() {
/* 16 */     return this.cn;
/*    */   }
/*    */   public void setSn(String sn) {
/* 19 */     this.sn = sn;
/*    */   }
/*    */   public String getSn() {
/* 22 */     return this.sn;
/*    */   }
/*    */   public void setDescription(String description) {
/* 25 */     this.description = description;
/*    */   }
/*    */   public String getDescription() {
/* 28 */     return this.description;
/*    */   }
/*    */   public void setGivenname(String givenname) {
/* 31 */     this.givenname = givenname;
/*    */   }
/*    */   public String getGivenname() {
/* 34 */     return this.givenname;
/*    */   }
/*    */   public void setMail(String mail) {
/* 37 */     this.mail = mail;
/*    */   }
/*    */   public String getMail() {
/* 40 */     return this.mail;
/*    */   }
/*    */   public void setManager(String manager) {
/* 43 */     this.manager = manager;
/*    */   }
/*    */   public String getManager() {
/* 46 */     return this.manager;
/*    */   }
/*    */   public void setUid(String uid) {
/* 49 */     this.uid = uid;
/*    */   }
/*    */   public String getUid() {
/* 52 */     return this.uid;
/*    */   }
/*    */   public void setUserpassword(String userpassword) {
/* 55 */     this.userpassword = userpassword;
/*    */   }
/*    */   public String getUserpassword() {
/* 58 */     return this.userpassword;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 62 */     StringBuilder sb = new StringBuilder();
/* 63 */     sb.append("Class " + getClass().getSimpleName());
/* 64 */     sb.append("-instance:");
/* 65 */     sb.append(hashCode() + ":");
/*    */ 
/* 67 */     sb.append("cn-" + this.cn);
/* 68 */     sb.append(",sn-" + this.sn);
/* 69 */     sb.append(",description-" + this.description);
/* 70 */     sb.append(",givenname-" + this.givenname);
/* 71 */     sb.append(",description-" + this.description);
/* 72 */     sb.append(", mail-" + this.mail);
/* 73 */     sb.append(",manager-" + this.manager);
/* 74 */     sb.append(",uid-" + this.uid);
/*    */ 
/* 76 */     return sb.toString();
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.domain.Person
 * JD-Core Version:    0.6.0
 */