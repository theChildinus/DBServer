CREATE TABLE REG_CLUSTER_LOCK (
             REG_LOCK_NAME VARCHAR2(20),
             REG_LOCK_STATUS VARCHAR2(20),
             REG_LOCKED_TIME TIMESTAMP,
             REG_TENANT_ID INTEGER DEFAULT 0,
             PRIMARY KEY (REG_LOCK_NAME))
/

CREATE TABLE REG_LOG (
             REG_LOG_ID INTEGER,
             REG_PATH VARCHAR2(2000),
             REG_USER_ID VARCHAR2(31) NOT NULL,
             REG_LOGGED_TIME TIMESTAMP  NOT NULL,
             REG_ACTION INTEGER NOT NULL,
             REG_ACTION_DATA VARCHAR2(500),
             REG_TENANT_ID INTEGER DEFAULT 0,
		 PRIMARY KEY (REG_LOG_ID))
/
CREATE SEQUENCE REG_LOG_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER REG_LOG_TRIGGER
                    BEFORE INSERT
                    ON REG_LOG
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                     BEGIN
                       SELECT REG_LOG_SEQUENCE.nextval INTO :NEW.REG_LOG_ID FROM dual;
           		   END;
/	         

CREATE TABLE REG_PATH(
             REG_PATH_ID INTEGER NULL,
             REG_PATH_VALUE VARCHAR2(2000) NOT NULL,
             REG_PATH_PARENT_ID INTEGER,
             REG_TENANT_ID INTEGER DEFAULT 0,
		 CONSTRAINT PK_REG_PATH PRIMARY KEY(REG_PATH_ID))
/
CREATE INDEX REG_PATH_IND_BY_PATH_VALUE ON REG_PATH(REG_PATH_VALUE)
/
CREATE INDEX REG_PATH_IND_BY_PARENT_ID ON REG_PATH(REG_PATH_PARENT_ID)
/
CREATE SEQUENCE REG_PATH_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER REG_PATH_TRIGGER
                    BEFORE INSERT
                    ON REG_PATH
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                     BEGIN
                       SELECT REG_PATH_SEQUENCE.nextval INTO :NEW.REG_PATH_ID FROM dual;
 			   END;
/                   

CREATE TABLE REG_CONTENT (
             REG_CONTENT_ID INTEGER,
             REG_CONTENT_DATA BLOB,
             REG_TENANT_ID INTEGER DEFAULT 0,
		 CONSTRAINT PK_REG_CONTENT PRIMARY KEY(REG_CONTENT_ID))
/
CREATE SEQUENCE REG_CONTENT_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER REG_CONTENT_TRIGGER
                    BEFORE INSERT
                    ON REG_CONTENT
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                     BEGIN
                       SELECT REG_CONTENT_SEQUENCE.nextval INTO :NEW.REG_CONTENT_ID FROM dual;
 			   END;
/                   

CREATE TABLE REG_CONTENT_HISTORY (
             REG_CONTENT_ID INTEGER NOT NULL,
             REG_CONTENT_DATA BLOB,
             REG_DELETED   SMALLINT,
             REG_TENANT_ID INTEGER DEFAULT 0,
		 CONSTRAINT PK_REG_CONTENT_HISTORY PRIMARY KEY(REG_CONTENT_ID))
/
CREATE TABLE REG_RESOURCE (
            REG_PATH_ID         INTEGER NOT NULL,
            REG_NAME            VARCHAR2(256),
            REG_VERSION         INTEGER,
            REG_MEDIA_TYPE      VARCHAR2(500),
            REG_CREATOR         VARCHAR2(31) NOT NULL,
            REG_CREATED_TIME    TIMESTAMP NOT NULL,
            REG_LAST_UPDATOR    VARCHAR2(31),
            REG_LAST_UPDATED_TIME    TIMESTAMP NOT NULL,
            REG_DESCRIPTION     VARCHAR2(1000),
            REG_CONTENT_ID      INTEGER,
            REG_TENANT_ID INTEGER DEFAULT 0,
            FOREIGN KEY (REG_PATH_ID) REFERENCES REG_PATH (REG_PATH_ID) ON DELETE CASCADE,
            FOREIGN KEY (REG_CONTENT_ID) REFERENCES REG_CONTENT (REG_CONTENT_ID) ON DELETE CASCADE,
		CONSTRAINT PK_REG_RESOURCE PRIMARY KEY(REG_VERSION))
/
CREATE SEQUENCE REG_RESOURCE_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER REG_RESOURCE_TRIGGER
                    BEFORE INSERT
                    ON REG_RESOURCE
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                     BEGIN
                       SELECT REG_RESOURCE_SEQUENCE.nextval INTO :NEW.REG_VERSION FROM dual;
 			   END;
/                   

CREATE INDEX REG_RESOURCE_IND_BY_NAME ON REG_RESOURCE(REG_NAME)
/
CREATE INDEX REG_RESOURCE_IND_BY_PATH_ID ON REG_RESOURCE(REG_PATH_ID, REG_NAME)
/
CREATE TABLE REG_RESOURCE_HISTORY (
            REG_PATH_ID         INTEGER NOT NULL,
            REG_NAME            VARCHAR2(256),
            REG_VERSION         INTEGER NOT NULL,
            REG_MEDIA_TYPE      VARCHAR2(500),
            REG_CREATOR         VARCHAR2(31) NOT NULL,
            REG_CREATED_TIME    TIMESTAMP NOT NULL,
            REG_LAST_UPDATOR    VARCHAR2(31),
            REG_LAST_UPDATED_TIME    TIMESTAMP NOT NULL,
            REG_DESCRIPTION     VARCHAR2(1000),
            REG_CONTENT_ID      INTEGER,
            REG_DELETED         SMALLINT,
            REG_TENANT_ID INTEGER DEFAULT 0,
		FOREIGN KEY (REG_PATH_ID) REFERENCES REG_PATH (REG_PATH_ID),
		FOREIGN KEY (REG_CONTENT_ID) REFERENCES REG_CONTENT_HISTORY (REG_CONTENT_ID),
            CONSTRAINT PK_REG_RESOURCE_HISTORY PRIMARY KEY(REG_VERSION))
/
CREATE INDEX REG_RES_HIST_IND_BY_NAME ON REG_RESOURCE_HISTORY(REG_NAME)
/
CREATE TABLE REG_COMMENT (
            REG_ID        INTEGER,
            REG_COMMENT_TEXT      VARCHAR2(500) NOT NULL,
            REG_USER_ID           VARCHAR2(31) NOT NULL,
            REG_COMMENTED_TIME    TIMESTAMP NOT NULL,
            REG_TENANT_ID INTEGER DEFAULT 0,
		CONSTRAINT PK_REG_COMMENT PRIMARY KEY(REG_ID))
/
CREATE SEQUENCE REG_COMMENT_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER REG_COMMENT_TRIGGER
                    BEFORE INSERT
                    ON REG_COMMENT
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                     BEGIN
                       SELECT REG_COMMENT_SEQUENCE.nextval INTO :NEW.REG_ID FROM dual;
 			   END;
/                   

CREATE TABLE REG_RESOURCE_COMMENT (
            REG_COMMENT_ID          INTEGER NOT NULL,
            REG_VERSION             INTEGER,
            REG_PATH_ID             INTEGER,
            REG_RESOURCE_NAME       VARCHAR2(256),
		FOREIGN KEY (REG_PATH_ID) REFERENCES REG_PATH (REG_PATH_ID),
            FOREIGN KEY (REG_COMMENT_ID) REFERENCES REG_COMMENT (REG_ID),
            REG_TENANT_ID INTEGER DEFAULT 0) 
/
CREATE INDEX REG_RES_COMM_BY_PATH_ID ON REG_RESOURCE_COMMENT(REG_PATH_ID, REG_RESOURCE_NAME) 
/
CREATE INDEX REG_RES_COMM_BY_VERSION ON REG_RESOURCE_COMMENT(REG_VERSION)
/
CREATE TABLE REG_RATING (
            REG_ID     INTEGER,
            REG_RATING        INTEGER NOT NULL,
            REG_USER_ID       VARCHAR2(31) NOT NULL,
            REG_RATED_TIME    TIMESTAMP NOT NULL,
            REG_TENANT_ID INTEGER DEFAULT 0,
            CONSTRAINT PK_REG_RATING PRIMARY KEY(REG_ID))
/
CREATE SEQUENCE REG_RATING_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER REG_RATING_TRIGGER
                    BEFORE INSERT
                    ON REG_RATING
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                     BEGIN
                       SELECT REG_RATING_SEQUENCE.nextval INTO :NEW.REG_ID FROM dual;
                     END;
/
				
CREATE TABLE REG_RESOURCE_RATING (
            REG_RATING_ID           INTEGER NOT NULL,
            REG_VERSION             INTEGER,
            REG_PATH_ID             INTEGER,
            REG_RESOURCE_NAME       VARCHAR2(256),
		FOREIGN KEY (REG_PATH_ID) REFERENCES REG_PATH (REG_PATH_ID),
		FOREIGN KEY (REG_RATING_ID) REFERENCES REG_RATING (REG_ID),
            REG_TENANT_ID INTEGER DEFAULT 0)
/
CREATE INDEX REG_RATING_IND_BY_PATH_ID ON REG_RESOURCE_RATING(REG_PATH_ID, REG_RESOURCE_NAME)
/
CREATE INDEX REG_RATING_IND_BY_VERSION ON REG_RESOURCE_RATING(REG_VERSION)
/
CREATE TABLE REG_TAG (
            REG_ID         INTEGER,
            REG_TAG_NAME       VARCHAR2(500) NOT NULL,
            REG_USER_ID        VARCHAR2(31) NOT NULL,
            REG_TAGGED_TIME    TIMESTAMP NOT NULL,
            REG_TENANT_ID INTEGER DEFAULT 0,
            CONSTRAINT PK_REG_TAG PRIMARY KEY(REG_ID))
/
CREATE SEQUENCE REG_TAG_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER REG_TAG_TRIGGER
                    BEFORE INSERT
                    ON REG_TAG
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                     BEGIN
                       SELECT REG_TAG_SEQUENCE.nextval INTO :NEW.REG_ID FROM dual;
                     END;
/  
			

CREATE TABLE REG_RESOURCE_TAG (
            REG_TAG_ID              INTEGER NOT NULL,
            REG_VERSION             INTEGER,
            REG_PATH_ID             INTEGER,
            REG_RESOURCE_NAME       VARCHAR2(256),
		FOREIGN KEY (REG_PATH_ID) REFERENCES REG_PATH (REG_PATH_ID),
		FOREIGN KEY (REG_TAG_ID) REFERENCES REG_TAG (REG_ID),
            REG_TENANT_ID INTEGER DEFAULT 0)
/
CREATE INDEX REG_TAG_IND_BY_PATH_ID ON REG_RESOURCE_TAG(REG_PATH_ID, REG_RESOURCE_NAME)
/
CREATE INDEX REG_TAG_IND_BY_VERSION ON REG_RESOURCE_TAG(REG_VERSION)
/
CREATE TABLE REG_PROPERTY (
            REG_ID         INTEGER,
            REG_NAME       VARCHAR2(100) NOT NULL,
            REG_VALUE        VARCHAR2(1000),
            REG_TENANT_ID INTEGER DEFAULT 0,
            CONSTRAINT PK_REG_PROPERTY PRIMARY KEY(REG_ID))
/

CREATE SEQUENCE REG_PROPERTY_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER REG_PROPERTY_TRIGGER
                    BEFORE INSERT
                    ON REG_PROPERTY
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                     BEGIN
                       SELECT REG_PROPERTY_SEQUENCE.nextval INTO :NEW.REG_ID FROM dual;
 			   END;
/                   

CREATE TABLE REG_RESOURCE_PROPERTY (
            REG_PROPERTY_ID         INTEGER NOT NULL,
            REG_VERSION             INTEGER,
            REG_PATH_ID             INTEGER,
            REG_RESOURCE_NAME       VARCHAR2(256),
		FOREIGN KEY (REG_PATH_ID) REFERENCES REG_PATH (REG_PATH_ID),
		FOREIGN KEY (REG_PROPERTY_ID) REFERENCES REG_PROPERTY (REG_ID),
            REG_TENANT_ID INTEGER DEFAULT 0)
/
CREATE INDEX REG_RESC_PROP_BY_VERN_PROPID ON REG_RESOURCE_PROPERTY(REG_PROPERTY_ID,REG_VERSION)
/
CREATE INDEX REG_RESC_PROP_BY_VERN_PATHNAME ON REG_RESOURCE_PROPERTY(REG_PROPERTY_ID,REG_PATH_ID,REG_RESOURCE_NAME)
/
CREATE TABLE REG_ASSOCIATION (
            REG_ASSOCIATION_ID INTEGER,
            REG_SOURCEPATH VARCHAR2 (2000) NOT NULL,
            REG_TARGETPATH VARCHAR2 (2000) NOT NULL,
            REG_ASSOCIATION_TYPE VARCHAR2 (2000) NOT NULL,
            REG_TENANT_ID INTEGER DEFAULT 0,
            PRIMARY KEY (REG_ASSOCIATION_ID))
/
CREATE SEQUENCE REG_ASSOCIATION_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER REG_ASSOCIATION_TRIGGER
                    BEFORE INSERT
                    ON REG_ASSOCIATION
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                     BEGIN
                       SELECT REG_ASSOCIATION_SEQUENCE.nextval INTO :NEW.REG_ASSOCIATION_ID FROM dual;
 			   END;
/                   

CREATE TABLE REG_SNAPSHOT (
            REG_SNAPSHOT_ID     INTEGER,
            REG_PATH_ID            INTEGER NOT NULL,
            REG_RESOURCE_NAME      VARCHAR2(256),
            REG_RESOURCE_VIDS     BLOB NOT NULL,
            REG_TENANT_ID INTEGER DEFAULT 0,
		FOREIGN KEY (REG_PATH_ID) REFERENCES REG_PATH (REG_PATH_ID),
            CONSTRAINT PK_REG_SNAPSHOT PRIMARY KEY(REG_SNAPSHOT_ID))
/
CREATE INDEX REG_SNAPSHOT_PATH_ID ON REG_SNAPSHOT(REG_PATH_ID, REG_RESOURCE_NAME)
/
CREATE SEQUENCE REG_SNAPSHOT_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER REG_SNAPSHOT_TRIGGER
                    BEFORE INSERT
                    ON REG_SNAPSHOT
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                     BEGIN
                       SELECT REG_SNAPSHOT_SEQUENCE.nextval INTO :NEW.REG_SNAPSHOT_ID FROM dual;
                     END;
/
				
CREATE TABLE UM_USER (
                    UM_ID INTEGER,
                    UM_USER_NAME VARCHAR2(255) NOT NULL,
                    UM_USER_PASSWORD VARCHAR2(255) NOT NULL,
			  UM_SALT_VALUE VARCHAR(31),
                    PRIMARY KEY (UM_ID),
                    UM_TENANT_ID INTEGER DEFAULT 0,
                    UNIQUE(UM_USER_NAME))
/
CREATE SEQUENCE UM_USER_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER UM_USER_TRIGGER
		            BEFORE INSERT
                    ON UM_USER
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                    BEGIN
                     SELECT UM_USER_SEQUENCE.nextval INTO :NEW.UM_ID FROM dual;
                    END;
/
		
CREATE TABLE UM_USER_ATTRIBUTE (
                    UM_ID INTEGER,
                    UM_ATTR_NAME VARCHAR2(255) NOT NULL,
                    UM_ATTR_VALUE VARCHAR2(255),
                    UM_PROFILE_ID VARCHAR(255),
                    UM_USER_ID INTEGER,
                    UM_TENANT_ID INTEGER DEFAULT 0,
                    FOREIGN KEY (UM_USER_ID) REFERENCES UM_USER(UM_ID) ON DELETE CASCADE,
                    PRIMARY KEY (UM_ID))
/
CREATE SEQUENCE UM_USER_ATTRIBUTE_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER UM_USER_ATTRIBUTE_TRIGGER
                    BEFORE INSERT
                    ON UM_USER_ATTRIBUTE
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                    BEGIN
                    SELECT UM_USER_ATTRIBUTE_SEQUENCE.nextval INTO :NEW.UM_ID FROM dual;
 			  END;
/                   

CREATE TABLE UM_ROLE (
                    UM_ID INTEGER,
                    UM_ROLE_NAME VARCHAR2(255) NOT NULL,
                    UM_TENANT_ID INTEGER DEFAULT 0,
                    PRIMARY KEY (UM_ID),
                    UNIQUE(UM_ROLE_NAME))
/
CREATE SEQUENCE UM_ROLE_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER UM_ROLE_TRIGGER
                    BEFORE INSERT
                    ON UM_ROLE
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                    BEGIN
                     SELECT UM_ROLE_SEQUENCE.nextval INTO :NEW.UM_ID FROM dual;
 			  END;
/                  

CREATE TABLE UM_ROLE_ATTRIBUTE (
                    UM_ID INTEGER,
                    UM_ATTR_NAME VARCHAR2(255) NOT NULL,
                    UM_ATTR_VALUE VARCHAR2(255),
                    UM_ROLE_ID INTEGER,
                    UM_TENANT_ID INTEGER DEFAULT 0,
                    FOREIGN KEY (UM_ROLE_ID) REFERENCES UM_ROLE(UM_ID) ON DELETE CASCADE,
                    PRIMARY KEY (UM_ID))
/
CREATE SEQUENCE UM_ROLE_ATTRIBUTE_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER UM_ROLE_ATTRIBUTE_TRIGGER
		            BEFORE INSERT
                    ON UM_ROLE_ATTRIBUTE
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                    BEGIN
                     SELECT UM_ROLE_ATTRIBUTE_SEQUENCE.nextval INTO :NEW.UM_ID FROM dual;
                    END;
/                  

CREATE TABLE UM_PERMISSION (
                    UM_ID INTEGER,
                    UM_RESOURCE_ID VARCHAR2(255) NOT NULL,
                    UM_ACTION VARCHAR2(255) NOT NULL,
                    UM_TENANT_ID INTEGER DEFAULT 0,
                    PRIMARY KEY (UM_ID))
/
CREATE SEQUENCE UM_PERMISSION_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER UM_PERMISSION_TRIGGER
                    BEFORE INSERT
                    ON UM_PERMISSION
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                    BEGIN
                     SELECT UM_PERMISSION_SEQUENCE.nextval INTO :NEW.UM_ID FROM dual;
                    END;
/   

CREATE TABLE UM_ROLE_PERMISSION (
		            UM_ID INTEGER,
                    UM_PERMISSION_ID INTEGER NOT NULL,
                    UM_ROLE_ID INTEGER NOT NULL,
                    UM_IS_ALLOWED SMALLINT NOT NULL,
                    UM_TENANT_ID INTEGER DEFAULT 0,
                    UNIQUE (UM_PERMISSION_ID, UM_ROLE_ID),
                    FOREIGN KEY (UM_PERMISSION_ID) REFERENCES UM_PERMISSION(UM_ID) ON DELETE  CASCADE,
                    FOREIGN KEY (UM_ROLE_ID) REFERENCES UM_ROLE(UM_ID) ON DELETE CASCADE,
                    PRIMARY KEY (UM_ID))
/
CREATE SEQUENCE UM_ROLE_PERMISSION_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER UM_ROLE_PERMISSION_TRIGGER
		            BEFORE INSERT
                    ON UM_ROLE_PERMISSION
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                    BEGIN
                    SELECT UM_ROLE_PERMISSION_SEQUENCE.nextval INTO :NEW.UM_ID FROM dual;
 			  END;
/                   

CREATE TABLE UM_USER_PERMISSION (
		            UM_ID INTEGER,
		            UM_PERMISSION_ID INTEGER NOT NULL,
                    UM_USER_ID INTEGER NOT NULL,
                    UM_IS_ALLOWED SMALLINT NOT NULL,
                    UM_TENANT_ID INTEGER DEFAULT 0,
                    UNIQUE (UM_PERMISSION_ID, UM_USER_ID),
                    FOREIGN KEY (UM_PERMISSION_ID) REFERENCES UM_PERMISSION(UM_ID) ON DELETE CASCADE,
                    FOREIGN KEY (UM_USER_ID) REFERENCES UM_USER(UM_ID) ON DELETE CASCADE,
                    PRIMARY KEY (UM_ID))
/
CREATE SEQUENCE UM_USER_PERMISSION_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER UM_USER_PERMISSION_TRIGGER
		            BEFORE INSERT
		            ON UM_USER_PERMISSION
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                    BEGIN
                    SELECT UM_USER_PERMISSION_SEQUENCE.nextval INTO :NEW.UM_ID FROM dual;
 			  END;
/                   

CREATE TABLE UM_USER_ROLE (
		            UM_ID INTEGER,
                    UM_ROLE_ID INTEGER NOT NULL,
                    UM_USER_ID INTEGER NOT NULL,
                    UM_TENANT_ID INTEGER DEFAULT 0,
                    UNIQUE (UM_USER_ID, UM_ROLE_ID),
                    FOREIGN KEY (UM_ROLE_ID) REFERENCES UM_ROLE(UM_ID) ON DELETE CASCADE,
                    FOREIGN KEY (UM_USER_ID) REFERENCES UM_USER(UM_ID) ON DELETE CASCADE,
                    PRIMARY KEY (UM_ID))
/
CREATE SEQUENCE UM_USER_ROLE_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER UM_USER_ROLE_TRIGGER
                    BEFORE INSERT
                    ON UM_USER_ROLE
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                    BEGIN
 	                   SELECT UM_USER_ROLE_SEQUENCE.nextval INTO :NEW.UM_ID FROM dual;
                    END;
/

CREATE TABLE HYBRID_ROLE (
                    UM_ID INTEGER,
                    UM_ROLE_ID VARCHAR(255) NOT NULL,
                    UM_TENANT_ID INTEGER DEFAULT 0,
                    PRIMARY KEY (UM_ID),
                    UNIQUE(UM_ROLE_ID))
/
CREATE SEQUENCE HYBRID_ROLE_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER HYBRID_ROLE_TRIGGER
                    BEFORE INSERT
                    ON HYBRID_ROLE
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                    BEGIN
                    SELECT HYBRID_ROLE_SEQUENCE.nextval INTO :NEW.UM_ID FROM dual;
                    END;
/  
			
CREATE TABLE HYBRID_USER_ROLE (
                    UM_ID INTEGER,
                    UM_USER_ID VARCHAR(255),
                    UM_ROLE_ID VARCHAR(255) NOT NULL,
                    UM_TENANT_ID INTEGER DEFAULT 0,
                    PRIMARY KEY (UM_ID))
/
CREATE SEQUENCE HYBRID_USER_ROLE_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER HYBRID_USER_ROLE_TRIGGER
                    BEFORE INSERT
                    ON HYBRID_USER_ROLE
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                    BEGIN
                    SELECT HYBRID_USER_ROLE_SEQUENCE.nextval INTO :NEW.UM_ID FROM dual;
			  END;
/                    

CREATE TABLE HYBRID_PERMISSION (
                    UM_ID INTEGER,UM_RESOURCE_ID VARCHAR(255),
                    UM_ACTION VARCHAR(255) NOT NULL,  
                    UM_TENANT_ID INTEGER DEFAULT 0,
                    PRIMARY KEY (UM_ID))

/
CREATE SEQUENCE HYBRID_PERMISSION_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER HYBRID_PERMISSION_TRIGGER
                     BEFORE INSERT
                     ON HYBRID_PERMISSION
                     REFERENCING NEW AS NEW   FOR EACH ROW
                     BEGIN
                     SELECT HYBRID_PERMISSION_SEQUENCE.nextval INTO :NEW.UM_ID FROM dual;
 			   END;
/			                    

CREATE TABLE HYBRID_ROLE_PERMISSION (
                     UM_ID INTEGER,
                     UM_PERMISSION_ID INTEGER NOT NULL,
                     UM_ROLE_ID VARCHAR(255) NOT NULL,
                     UM_IS_ALLOWED SMALLINT NOT NULL,
                     UM_TENANT_ID INTEGER DEFAULT 0,
                     UNIQUE (UM_PERMISSION_ID, UM_ROLE_ID),
                     FOREIGN KEY (UM_PERMISSION_ID) REFERENCES HYBRID_PERMISSION(UM_ID) ON DELETE  CASCADE,
                     PRIMARY KEY (UM_ID))
/
CREATE SEQUENCE HYBRID_ROLE_P_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER HYBRID_ROLE_P_TRIGGER
                     BEFORE INSERT
                     ON HYBRID_ROLE_PERMISSION
                     REFERENCING NEW AS NEW   FOR EACH ROW
                     BEGIN
                     SELECT HYBRID_ROLE_P_SEQUENCE.nextval INTO :NEW.UM_ID FROM dual;
                     END;
/

CREATE TABLE HYBRID_USER_PERMISSION (
                     UM_ID INTEGER,
                     UM_PERMISSION_ID INTEGER NOT NULL,
                     UM_USER_ID VARCHAR(255) NOT NULL,
                     UM_IS_ALLOWED SMALLINT NOT NULL,
                     UM_TENANT_ID INTEGER DEFAULT 0,
                     UNIQUE (UM_PERMISSION_ID, UM_USER_ID),
                     FOREIGN KEY (UM_PERMISSION_ID) REFERENCES HYBRID_PERMISSION(UM_ID) ON DELETE CASCADE,
                     PRIMARY KEY (UM_ID))
/
CREATE SEQUENCE HYBRID_USER_P_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER HYBRID_USER_P_TRIGGER
                     BEFORE INSERT
                     ON HYBRID_USER_PERMISSION
                     REFERENCING NEW AS NEW FOR EACH ROW
                     BEGIN
			     SELECT HYBRID_USER_P_SEQUENCE.nextval INTO :NEW.UM_ID FROM dual;
                     END;
/

CREATE TABLE UM_USER_DATA (
					UM_ID INTEGER, 
					UM_USER_ID INTEGER NOT NULL,
					UM_EMAIL VARCHAR(255),
					UM_FIRST_NAME VARCHAR(255),
					UM_LAST_NAME VARCHAR(255),
					UM_BIRTH_DATE VARCHAR(255),
					UM_FULL_NAME VARCHAR(255),
					UM_NAME_PREFIX VARCHAR(255),
					UM_GENDER VARCHAR(10),
					UM_TIME_ZONE VARCHAR(255),
					UM_COMPANY_NAME VARCHAR(255),
					UM_JOB_TITLE VARCHAR(255),
					UM_PRIMARY_PHONE VARCHAR(255),
					UM_HOME_PHONE VARCHAR(255),
					UM_WORK_PHONE VARCHAR(255),
					UM_MOBILE_PHONE VARCHAR(255),
					UM_STREET_ADDRESS VARCHAR(255),
					UM_CITY VARCHAR(255),
					UM_STATE VARCHAR(255),
					UM_COUNTRY VARCHAR(255),
					UM_POSTAL_CODE VARCHAR(255),
					UM_WEB_PAGE VARCHAR(255),
					UM_LANGUAGE VARCHAR(255),
					UM_BLOG VARCHAR(255),
					UM_PROFILE_ID VARCHAR(255),
                                        UM_TENANT_ID INTEGER DEFAULT 0,
					FOREIGN KEY (UM_USER_ID) REFERENCES UM_USER(UM_ID),
					PRIMARY KEY (UM_ID)
)
/
CREATE SEQUENCE UM_USER_DATA_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER UM_USER_DATA_TRIGGER
                    BEFORE INSERT
                    ON UM_USER_DATA
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                    BEGIN
                    SELECT UM_USER_DATA_SEQUENCE.nextval INTO :NEW.UM_ID FROM dual;
                    END;
/
CREATE TABLE UM_BIN_DATA (
            UM_ID INTEGER,
            UM_USER_ID INTEGER NOT NULL,
            UM_CONTENT_NAME VARCHAR(255) NOT NULL,
            UM_CONTENT BLOB NOT NULL,
            UM_PROFILE_ID VARCHAR(255) NOT NULL,
            UM_TENANT_ID INTEGER DEFAULT 0,
            FOREIGN KEY (UM_USER_ID) REFERENCES UM_USER(UM_ID),
            PRIMARY KEY (UM_ID)
)
/
CREATE SEQUENCE UM_BIN_DATA_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER UM_BIN_DATA_TRIGGER
                    BEFORE INSERT
                    ON UM_BIN_DATA
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                    BEGIN
                    SELECT UM_BIN_DATA_SEQUENCE.nextval INTO :NEW.UM_ID FROM dual;
 			  END;
/                   

CREATE TABLE UM_DIALECT(
            UM_ID INTEGER, 
            UM_DIALECT_URI VARCHAR(255) NOT NULL,
            UM_REALM VARCHAR(63) NOT NULL,
            UM_TENANT_ID INTEGER DEFAULT 0,
            UNIQUE(UM_DIALECT_URI, UM_REALM),
            PRIMARY KEY (UM_ID)
)
/
CREATE SEQUENCE UM_DIALECT_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER UM_DIALECT_TRIGGER
                    BEFORE INSERT
                    ON UM_DIALECT
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                    BEGIN
                    SELECT UM_DIALECT_SEQUENCE.nextval INTO :NEW.UM_ID FROM dual;
  			  END;
/                  

CREATE TABLE UM_CLAIM(
            UM_ID INTEGER, 
            UM_DIALECT_ID INTEGER NOT NULL, 
            UM_CLAIM_URI VARCHAR(255) NOT NULL, 
            UM_DISPLAY_TAG VARCHAR(255), 
            UM_DESCRIPTION VARCHAR(255), 
            UM_MAPPED_ATTRIBUTE VARCHAR(255), 
            UM_REG_EX VARCHAR(255), 
            UM_SUPPORTED SMALLINT, 
            UM_REQUIRED SMALLINT, 
            UM_DISPLAY_ORDER INTEGER,
            UM_TENANT_ID INTEGER DEFAULT 0,
            UNIQUE(UM_DIALECT_ID, UM_CLAIM_URI), 
            FOREIGN KEY(UM_DIALECT_ID) REFERENCES UM_DIALECT(UM_ID), 
            PRIMARY KEY (UM_ID)
)
/
CREATE SEQUENCE UM_CLAIM_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER UM_CLAIM_TRIGGER
                    BEFORE INSERT
                    ON UM_CLAIM
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                    BEGIN
	                SELECT UM_CLAIM_SEQUENCE.nextval INTO :NEW.UM_ID FROM dual;
      		  END;
/              

CREATE TABLE UM_PROFILE_CONFIG(
            UM_ID INTEGER, 
            UM_DIALECT_ID INTEGER, 
            UM_PROFILE_NAME VARCHAR(255), 
            UM_TENANT_ID INTEGER DEFAULT 0,
            FOREIGN KEY(UM_DIALECT_ID) REFERENCES UM_DIALECT(UM_ID), 
            PRIMARY KEY (UM_ID)
)
/
CREATE SEQUENCE UM_PROFILE_CONFIG_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER UM_PROFILE_CONFIG_TRIGGER
                    BEFORE INSERT
                    ON UM_PROFILE_CONFIG
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                    BEGIN
                    SELECT UM_PROFILE_CONFIG_SEQUENCE.nextval INTO :NEW.UM_ID FROM dual;
                    END;
/
    
CREATE TABLE UM_CLAIM_BEHAVIOR(
            UM_ID INTEGER, 
            UM_PROFILE_ID INTEGER, 
            UM_CLAIM_ID INTEGER, 
            UM_BEHAVIOUR SMALLINT,
            UM_TENANT_ID INTEGER DEFAULT 0,
            FOREIGN KEY(UM_PROFILE_ID) REFERENCES UM_PROFILE_CONFIG(UM_ID), 
            FOREIGN KEY(UM_CLAIM_ID) REFERENCES UM_CLAIM(UM_ID), 
            PRIMARY KEY (UM_ID)
)
/
CREATE SEQUENCE UM_CLAIM_BEHAVIOR_SEQUENCE START WITH 1 INCREMENT BY 1 NOCACHE
/
CREATE OR REPLACE TRIGGER UM_CLAIM_BEHAVIOR_TRIGGER
                    BEFORE INSERT
                    ON UM_CLAIM_BEHAVIOR
                    REFERENCING NEW AS NEW
                    FOR EACH ROW
                    BEGIN
                    SELECT UM_CLAIM_BEHAVIOR_SEQUENCE.nextval INTO :NEW.UM_ID FROM dual;
 			  END;
/                   


