/*    */ package bupt.intt.wsmonitor.servicemonitor.domain;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class ConfigType
/*    */ {
/*    */   private static final long serialVersionUID = -7743687609410105617L;
/*    */   private String name;
/*    */   private String description;
/*    */   private String catetory;
/*    */   private String computerName;
/*    */   public static final String OBJECTCLASS = "organizationalUnit";
/*    */   private ArrayList<PathName> disNames;
/*    */ 
/*    */   public void setName(String name)
/*    */   {
/* 24 */     this.name = name;
/*    */   }
/*    */   public String getName() {
/* 27 */     return this.name;
/*    */   }
/*    */   public void setDescription(String description) {
/* 30 */     this.description = description;
/*    */   }
/*    */   public String getDescription() {
/* 33 */     return this.description;
/*    */   }
/*    */ 
/*    */   public void setDisNames(ArrayList<PathName> disNames)
/*    */   {
/* 38 */     this.disNames = disNames;
/*    */   }
/*    */   public ArrayList<PathName> getDisNames() {
/* 41 */     return this.disNames;
/*    */   }
/*    */ 
/*    */   public void setDisNames(String nameString)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void setCatetory(String catetory) {
/* 49 */     this.catetory = catetory;
/*    */   }
/*    */   public String getCatetory() {
/* 52 */     return this.catetory;
/*    */   }
/*    */ 
/*    */   public void setComputerName(String computerName) {
/* 56 */     this.computerName = computerName;
/*    */   }
/*    */   public String getComputerName() {
/* 59 */     return this.computerName;
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.domain.ConfigType
 * JD-Core Version:    0.6.0
 */