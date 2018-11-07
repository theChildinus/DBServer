/*    */ package bupt.intt.wsmonitor.servicemonitor.domain;
/*    */ 
/*    */ public class PathName
/*    */ {
/*    */   private String prefix;
/*    */   private String value;
/*    */ 
/*    */   public PathName()
/*    */   {
/*    */   }
/*    */ 
/*    */   public PathName(String prefix, String value)
/*    */   {
/*  9 */     this.prefix = prefix;
/* 10 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public void setPrefix(String prefix)
/*    */   {
/* 16 */     this.prefix = prefix;
/*    */   }
/*    */   public String getPrefix() {
/* 19 */     return this.prefix;
/*    */   }
/*    */   public void setValue(String value) {
/* 22 */     this.value = value;
/*    */   }
/*    */   public String getValue() {
/* 25 */     return this.value;
/*    */   }
/*    */ }

/* Location:           D:\jd-gui-0.3.3.windows\manageUIlibs\ldapHelper.jar
 * Qualified Name:     bupt.intt.wsmonitor.servicemonitor.domain.PathName
 * JD-Core Version:    0.6.0
 */