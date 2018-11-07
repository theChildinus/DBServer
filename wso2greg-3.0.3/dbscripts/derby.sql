CREATE TABLE REG_CLUSTER_LOCK (
             REG_LOCK_NAME VARCHAR (20),
             REG_LOCK_STATUS VARCHAR (20),
             REG_LOCKED_TIME TIMESTAMP,
             REG_TENANT_ID INTEGER DEFAULT 0,
             PRIMARY KEY (REG_LOCK_NAME)
);

CREATE TABLE REG_LOG (
             REG_LOG_ID INTEGER GENERATED ALWAYS AS IDENTITY,
             REG_PATH VARCHAR (2000),
             REG_USER_ID VARCHAR (31) NOT NULL,
             REG_LOGGED_TIME TIMESTAMP NOT NULL,
             REG_ACTION INTEGER NOT NULL,
             REG_ACTION_DATA VARCHAR (500),
             REG_TENANT_ID INTEGER DEFAULT 0,
             PRIMARY KEY (REG_LOG_ID)
);

CREATE TABLE REG_PATH(
             REG_PATH_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
             REG_PATH_VALUE VARCHAR(2000) NOT NULL,
             REG_PATH_PARENT_ID INT,
             REG_TENANT_ID INTEGER DEFAULT 0,
             CONSTRAINT PK_REG_PATH PRIMARY KEY(REG_PATH_ID)
);
CREATE INDEX REG_PATH_IND_BY_PATH_VALUE ON REG_PATH(REG_PATH_VALUE);
CREATE INDEX REG_PATH_IND_BY_PARENT_ID ON REG_PATH(REG_PATH_PARENT_ID);

CREATE TABLE REG_CONTENT (
             REG_CONTENT_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
             REG_CONTENT_DATA BLOB,
             REG_TENANT_ID INTEGER DEFAULT 0,
             CONSTRAINT PK_REG_CONTENT PRIMARY KEY(REG_CONTENT_ID)
);

CREATE TABLE REG_CONTENT_HISTORY (
             REG_CONTENT_ID INTEGER NOT NULL,
             REG_CONTENT_DATA BLOB,
             REG_DELETED   SMALLINT,
             REG_TENANT_ID INTEGER DEFAULT 0,
             CONSTRAINT PK_REG_CONTENT_HISTORY PRIMARY KEY(REG_CONTENT_ID)
);

CREATE TABLE REG_RESOURCE (
            REG_PATH_ID         INTEGER NOT NULL,
            REG_NAME            VARCHAR(256),
            REG_VERSION         INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
            REG_MEDIA_TYPE      VARCHAR(500),
            REG_CREATOR         VARCHAR(31) NOT NULL,
            REG_CREATED_TIME    TIMESTAMP NOT NULL,
            REG_LAST_UPDATOR    VARCHAR(31),
            REG_LAST_UPDATED_TIME    TIMESTAMP NOT NULL,
            REG_DESCRIPTION     VARCHAR(1000),
            REG_CONTENT_ID      INTEGER,
            REG_TENANT_ID INTEGER DEFAULT 0,
            CONSTRAINT PK_REG_RESOURCE PRIMARY KEY(REG_VERSION)
);

ALTER TABLE REG_RESOURCE ADD CONSTRAINT REG_RESOURCE_FK_BY_PATH_ID FOREIGN KEY (REG_PATH_ID) REFERENCES REG_PATH (REG_PATH_ID);
ALTER TABLE REG_RESOURCE ADD CONSTRAINT REG_RESOURCE_FK_BY_CONTENT_ID FOREIGN KEY (REG_CONTENT_ID) REFERENCES REG_CONTENT (REG_CONTENT_ID);
CREATE INDEX REG_RESOURCE_IND_BY_NAME ON REG_RESOURCE(REG_NAME);
CREATE INDEX REG_RESOURCE_IND_BY_PATH_ID_NAME ON REG_RESOURCE(REG_PATH_ID, REG_NAME);

CREATE TABLE REG_RESOURCE_HISTORY (
            REG_PATH_ID         INTEGER NOT NULL,
            REG_NAME            VARCHAR(256),
            REG_VERSION         INTEGER DEFAULT 0,
            REG_MEDIA_TYPE      VARCHAR(500),
            REG_CREATOR         VARCHAR(31) NOT NULL,
            REG_CREATED_TIME    TIMESTAMP NOT NULL,
            REG_LAST_UPDATOR    VARCHAR(31),
            REG_LAST_UPDATED_TIME    TIMESTAMP NOT NULL,
            REG_DESCRIPTION     VARCHAR(1000),
            REG_CONTENT_ID      INTEGER,
            REG_DELETED         SMALLINT,
            REG_TENANT_ID INTEGER DEFAULT 0,
            CONSTRAINT PK_REG_RESOURCE_HISTORY PRIMARY KEY(REG_VERSION)
);

ALTER TABLE REG_RESOURCE_HISTORY ADD CONSTRAINT REG_RESOURCE_HIST_FK_BY_PATHID FOREIGN KEY (REG_PATH_ID) REFERENCES REG_PATH (REG_PATH_ID);
ALTER TABLE REG_RESOURCE_HISTORY ADD CONSTRAINT REG_RESOURCE_HIST_FK_BY_CONTENT_ID FOREIGN KEY (REG_CONTENT_ID) REFERENCES REG_CONTENT_HISTORY (REG_CONTENT_ID);
CREATE INDEX REG_RESOURCE_HISTORY_IND_BY_NAME ON REG_RESOURCE_HISTORY(REG_NAME);
CREATE INDEX REG_RESOURCE_HISTORY_IND_BY_PATH_ID_NAME ON REG_RESOURCE(REG_PATH_ID, REG_NAME);

CREATE TABLE REG_COMMENT (
            REG_ID        INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
            REG_COMMENT_TEXT      VARCHAR(500) NOT NULL,
            REG_USER_ID           VARCHAR(31) NOT NULL,
            REG_COMMENTED_TIME    TIMESTAMP NOT NULL,
            REG_TENANT_ID INTEGER DEFAULT 0,
            CONSTRAINT PK_REG_COMMENT PRIMARY KEY(REG_ID)
);

CREATE TABLE REG_RESOURCE_COMMENT (
            REG_COMMENT_ID          INTEGER NOT NULL,
            REG_VERSION             INTEGER DEFAULT 0,
            REG_PATH_ID             INTEGER,
            REG_RESOURCE_NAME       VARCHAR(256),
            REG_TENANT_ID INTEGER DEFAULT 0,
            CONSTRAINT PK_REG_RESOURCE_COMMENT PRIMARY KEY(REG_VERSION, REG_COMMENT_ID)
);


ALTER TABLE REG_RESOURCE_COMMENT ADD CONSTRAINT REG_RESOURCE_COMMENT_FK_BY_PATH_ID FOREIGN KEY (REG_PATH_ID) REFERENCES REG_PATH (REG_PATH_ID);
ALTER TABLE REG_RESOURCE_COMMENT ADD CONSTRAINT REG_RESOURCE_COMMENT_FK_BY_COMMENT_ID FOREIGN KEY (REG_COMMENT_ID) REFERENCES REG_COMMENT (REG_ID);
CREATE INDEX REG_RESOURCE_COMMENT_IND_BY_PATH_ID_AND_RESOURCE_NAME ON REG_RESOURCE_COMMENT(REG_PATH_ID, REG_RESOURCE_NAME);
CREATE INDEX REG_RESOURCE_COMMENT_IND_BY_VERSION ON REG_RESOURCE_COMMENT(REG_VERSION);

CREATE TABLE REG_RATING (
            REG_ID     INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
            REG_RATING        INTEGER NOT NULL,
            REG_USER_ID       VARCHAR(31) NOT NULL,
            REG_RATED_TIME    TIMESTAMP NOT NULL,
            REG_TENANT_ID INTEGER DEFAULT 0,
            CONSTRAINT PK_REG_RATING PRIMARY KEY(REG_ID)
);

CREATE TABLE REG_RESOURCE_RATING (
            REG_RATING_ID           INTEGER NOT NULL,
            REG_VERSION             INTEGER DEFAULT 0,
            REG_PATH_ID             INTEGER,
            REG_RESOURCE_NAME       VARCHAR(256),
            REG_TENANT_ID INTEGER DEFAULT 0,
            CONSTRAINT PK_REG_RESOURCE_RATING PRIMARY KEY(REG_VERSION, REG_RATING_ID)
);

ALTER TABLE REG_RESOURCE_RATING ADD CONSTRAINT REG_RESOURCE_RATING_FK_BY_PATH_ID FOREIGN KEY (REG_PATH_ID) REFERENCES REG_PATH (REG_PATH_ID);
ALTER TABLE REG_RESOURCE_RATING ADD CONSTRAINT REG_RESOURCE_RATING_FK_BY_RATING_ID FOREIGN KEY (REG_RATING_ID) REFERENCES REG_RATING (REG_ID);
CREATE INDEX REG_RESOURCE_RATING_IND_BY_PATH_ID_AND_RESOURCE_NAME ON REG_RESOURCE_RATING(REG_PATH_ID, REG_RESOURCE_NAME);
CREATE INDEX REG_RESOURCE_RATING_IND_BY_VERSION ON REG_RESOURCE_RATING(REG_VERSION);

CREATE TABLE REG_TAG (
            REG_ID         INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
            REG_TAG_NAME       VARCHAR(500) NOT NULL,
            REG_USER_ID        VARCHAR(31) NOT NULL,
            REG_TAGGED_TIME    TIMESTAMP NOT NULL,
            REG_TENANT_ID INTEGER DEFAULT 0,
            CONSTRAINT PK_REG_TAG PRIMARY KEY(REG_ID)
);

CREATE TABLE REG_RESOURCE_TAG (
            REG_TAG_ID              INTEGER NOT NULL,
            REG_VERSION             INTEGER DEFAULT 0,
            REG_PATH_ID             INTEGER,
            REG_RESOURCE_NAME       VARCHAR(256),
            REG_TENANT_ID INTEGER DEFAULT 0,
            CONSTRAINT PK_REG_RESOURCE_TAG PRIMARY KEY(REG_VERSION, REG_TAG_ID)
);

ALTER TABLE REG_RESOURCE_TAG ADD CONSTRAINT REG_RESOURCE_TAG_FK_BY_PATH_ID FOREIGN KEY (REG_PATH_ID) REFERENCES REG_PATH (REG_PATH_ID);
ALTER TABLE REG_RESOURCE_TAG ADD CONSTRAINT REG_RESOURCE_TAG_FK_BY_TAG_ID FOREIGN KEY (REG_TAG_ID) REFERENCES REG_TAG (REG_ID);
CREATE INDEX REG_RESOURCE_TAG_IND_BY_PATH_ID_AND_RESOURCE_NAME ON REG_RESOURCE_TAG(REG_PATH_ID, REG_RESOURCE_NAME);
CREATE INDEX REG_RESOURCE_TAG_IND_BY_VERSION ON REG_RESOURCE_TAG(REG_VERSION);

CREATE TABLE REG_PROPERTY (
            REG_ID         INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
            REG_NAME       VARCHAR(100) NOT NULL,
            REG_VALUE        VARCHAR(1000),
            REG_TENANT_ID INTEGER DEFAULT 0,
            CONSTRAINT PK_REG_PROPERTY PRIMARY KEY(REG_ID)
);

CREATE TABLE REG_RESOURCE_PROPERTY (
            REG_PROPERTY_ID         INTEGER NOT NULL,
            REG_VERSION             INTEGER DEFAULT 0,
            REG_PATH_ID             INTEGER,
            REG_RESOURCE_NAME       VARCHAR(256),
            REG_TENANT_ID INTEGER DEFAULT 0,
            CONSTRAINT PK_REG_RESOURCE_PROPERTY PRIMARY KEY(REG_VERSION, REG_PROPERTY_ID)
);

ALTER TABLE REG_RESOURCE_PROPERTY ADD CONSTRAINT REG_RESOURCE_PROPERTY_FK_BY_PATH_ID FOREIGN KEY (REG_PATH_ID) REFERENCES REG_PATH (REG_PATH_ID);
ALTER TABLE REG_RESOURCE_PROPERTY ADD CONSTRAINT REG_RESOURCE_PROPERTY_FK_BY_TAG_ID FOREIGN KEY (REG_PROPERTY_ID) REFERENCES REG_PROPERTY (REG_ID);
CREATE INDEX REG_RESOURCE_PROPERTY_IND_BY_PATH_ID_AND_RESOURCE_NAME ON REG_RESOURCE_PROPERTY(REG_PATH_ID, REG_RESOURCE_NAME);
CREATE INDEX REG_RESOURCE_PROPERTY_IND_BY_VERSION ON REG_RESOURCE_PROPERTY(REG_VERSION);

-- CREATE TABLE REG_ASSOCIATION (
-- SRC_PATH_ID     INTEGER,
-- SRC_RESOURCE_NAME    VARCHAR(256),
-- SRC_VERSION     INTEGER,
-- TGT_PATH_ID     INTEGER,
-- TGT_RESOURCE_NAME    VARCHAR(256),
-- TGT_VERSION     INTEGER
-- );
--
-- ALTER TABLE REG_ASSOCIATION ADD CONSTRAINT REG_ASSOCIATION_FK_BY_SRC_PATH_ID FOREIGN KEY (SRC_PATH_ID) REFERENCES REG_PATH (PATH_ID);
-- ALTER TABLE REG_ASSOCIATION ADD CONSTRAINT REG_ASSOCIATION_FK_BY_TGT_PATH_ID FOREIGN KEY (TGT_PATH_ID) REFERENCES REG_PATH (PATH_ID);
-- CREATE INDEX REG_ASSOCIATION_IND_BY_SRC_VERSION ON REG_ASSOCIATION(SRC_VERSION);
-- CREATE INDEX REG_ASSOCIATION_IND_BY_TGT_VERSION ON REG_ASSOCIATION(TGT_VERSION);
-- CREATE INDEX REG_ASSOCIATION_IND_BY_SRC_RESOURCE_NAME ON REG_ASSOCIATION(SRC_RESOURCE_NAME);
-- CREATE INDEX REG_ASSOCIATION_IND_BY_TGT_RESOURCE_NAME ON REG_ASSOCIATION(TGT_RESOURCE_NAME);



CREATE TABLE REG_ASSOCIATION (
            REG_ASSOCIATION_ID INTEGER GENERATED ALWAYS AS IDENTITY,
            REG_SOURCEPATH VARCHAR (2000) NOT NULL,
            REG_TARGETPATH VARCHAR (2000) NOT NULL,
            REG_ASSOCIATION_TYPE VARCHAR (2000) NOT NULL,
            REG_TENANT_ID INTEGER DEFAULT 0,
            PRIMARY KEY (REG_ASSOCIATION_ID)
);

CREATE TABLE REG_SNAPSHOT (
            REG_SNAPSHOT_ID     INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY,
            REG_PATH_ID            INTEGER NOT NULL,
            REG_RESOURCE_NAME       VARCHAR(256),
            REG_RESOURCE_VIDS     BLOB NOT NULL,
            REG_TENANT_ID INTEGER DEFAULT 0,
            CONSTRAINT PK_REG_SNAPSHOT PRIMARY KEY(REG_SNAPSHOT_ID)
);

CREATE INDEX REG_SNAPSHOT_IND_BY_PATH_ID_AND_RESOURCE_NAME ON REG_SNAPSHOT(REG_PATH_ID, REG_RESOURCE_NAME);

ALTER TABLE REG_SNAPSHOT ADD CONSTRAINT REG_SNAPSHOT_FK_BY_PATH_ID FOREIGN KEY (REG_PATH_ID) REFERENCES REG_PATH (REG_PATH_ID);


-- ################################
-- USER MANAGER TABLES
-- ################################

CREATE TABLE UM_USER (
             UM_ID INTEGER GENERATED ALWAYS AS IDENTITY,
             UM_USER_NAME VARCHAR(255) NOT NULL,
             UM_USER_PASSWORD VARCHAR(255) NOT NULL,
             UM_SALT_VALUE VARCHAR(31),
             UM_TENANT_ID INTEGER DEFAULT 0,
             PRIMARY KEY (UM_ID),
             UNIQUE(UM_USER_NAME)
);

CREATE TABLE UM_ROLE (
             UM_ID INTEGER GENERATED ALWAYS AS IDENTITY,
             UM_ROLE_NAME VARCHAR(255) NOT NULL,
             UM_TENANT_ID INTEGER DEFAULT 0,
             PRIMARY KEY (UM_ID),
             UNIQUE(UM_ROLE_NAME)
);

CREATE TABLE UM_ROLE_ATTRIBUTE (
             UM_ID INTEGER GENERATED ALWAYS AS IDENTITY,
             UM_ATTR_NAME VARCHAR(255) NOT NULL,
             UM_ATTR_VALUE VARCHAR(255),
             UM_ROLE_ID INTEGER,
             UM_TENANT_ID INTEGER DEFAULT 0,
             FOREIGN KEY (UM_ROLE_ID) REFERENCES UM_ROLE(UM_ID),
             PRIMARY KEY (UM_ID)
);

CREATE TABLE UM_PERMISSION (
             UM_ID INTEGER GENERATED ALWAYS AS IDENTITY,
             UM_RESOURCE_ID VARCHAR(255) NOT NULL,
             UM_ACTION VARCHAR(255) NOT NULL,
             UM_TENANT_ID INTEGER DEFAULT 0,
             PRIMARY KEY (UM_ID)
);

CREATE INDEX INDEX_UM_PERMISSION_UM_RESOURCE_ID_UM_ACTION
                    ON UM_PERMISSION (UM_RESOURCE_ID, UM_ACTION);

CREATE TABLE UM_ROLE_PERMISSION (
             UM_ID INTEGER GENERATED ALWAYS AS IDENTITY,
             UM_PERMISSION_ID INTEGER NOT NULL,
             UM_ROLE_ID INTEGER NOT NULL,
             UM_IS_ALLOWED SMALLINT NOT NULL,
             UM_TENANT_ID INTEGER DEFAULT 0,
             --UNIQUE (PERMISSION_ID, ROLE_ID),
             FOREIGN KEY (UM_PERMISSION_ID) REFERENCES UM_PERMISSION(UM_ID) ON DELETE CASCADE,
             FOREIGN KEY (UM_ROLE_ID) REFERENCES UM_ROLE(UM_ID),
             PRIMARY KEY (UM_ID)
);

CREATE TABLE UM_USER_PERMISSION (
             UM_ID INTEGER GENERATED ALWAYS AS IDENTITY,
             UM_PERMISSION_ID INTEGER NOT NULL,
             UM_USER_ID INTEGER NOT NULL,
             UM_IS_ALLOWED SMALLINT NOT NULL,
             UM_TENANT_ID INTEGER DEFAULT 0,
             --UNIQUE (PERMISSION_ID, USER_ID),
             FOREIGN KEY (UM_PERMISSION_ID) REFERENCES UM_PERMISSION(UM_ID) ON DELETE CASCADE ,
             FOREIGN KEY (UM_USER_ID) REFERENCES UM_USER(UM_ID),
             PRIMARY KEY (UM_ID)
);

CREATE TABLE UM_USER_ROLE (
             UM_ID INTEGER GENERATED ALWAYS AS IDENTITY,
             UM_ROLE_ID INTEGER NOT NULL,
             UM_USER_ID INTEGER NOT NULL,
             UM_TENANT_ID INTEGER DEFAULT 0,
             UNIQUE (UM_USER_ID, UM_ROLE_ID),
             FOREIGN KEY (UM_ROLE_ID) REFERENCES UM_ROLE(UM_ID),
             FOREIGN KEY (UM_USER_ID) REFERENCES UM_USER(UM_ID),
             PRIMARY KEY (UM_ID)
);


CREATE TABLE UM_USER_DATA (
       UM_ID INTEGER GENERATED BY DEFAULT AS IDENTITY, 
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
       UM_UM_TENANT_ID INTEGER DEFAULT 0,
       FOREIGN KEY (UM_USER_ID) REFERENCES UM_USER(UM_ID),
       PRIMARY KEY (UM_ID)
);


CREATE TABLE UM_BIN_DATA (
            UM_ID INTEGER GENERATED BY DEFAULT AS IDENTITY,
            UM_USER_ID INTEGER NOT NULL,
            UM_CONTENT_NAME VARCHAR(255) NOT NULL,
            UM_CONTENT BLOB NOT NULL,
            UM_PROFILE_ID VARCHAR(255) NOT NULL,
            UM_TENANT_ID INTEGER DEFAULT 0,
            FOREIGN KEY (UM_USER_ID) REFERENCES UM_USER(UM_ID),
            PRIMARY KEY (UM_ID)
);


CREATE TABLE UM_USER_ATTRIBUTE (
            UM_ID INTEGER GENERATED BY DEFAULT AS IDENTITY,
            UM_ATTR_NAME VARCHAR(255) NOT NULL,
            UM_ATTR_VALUE VARCHAR(1024), 
            UM_PROFILE_ID VARCHAR(255), 
            UM_USER_ID INTEGER,
            UM_TENANT_ID INTEGER DEFAULT 0,
            FOREIGN KEY (UM_USER_ID) REFERENCES UM_USER(UM_ID),
            PRIMARY KEY (UM_ID)
);



CREATE TABLE UM_DIALECT(
            UM_ID INTEGER GENERATED BY DEFAULT AS IDENTITY, 
            UM_DIALECT_URI VARCHAR(255) NOT NULL,
            UM_REALM VARCHAR(63) NOT NULL,
            UM_TENANT_ID INTEGER DEFAULT 0,
            UNIQUE(UM_DIALECT_URI, UM_REALM),
            PRIMARY KEY (UM_ID)
);

CREATE TABLE UM_CLAIM(
            UM_ID INTEGER GENERATED BY DEFAULT AS IDENTITY, 
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
);

CREATE TABLE UM_PROFILE_CONFIG(
            UM_ID INTEGER GENERATED BY DEFAULT AS IDENTITY, 
            UM_DIALECT_ID INTEGER, 
            UM_PROFILE_NAME VARCHAR(255), 
            UM_TENANT_ID INTEGER DEFAULT 0,
            FOREIGN KEY(UM_DIALECT_ID) REFERENCES UM_DIALECT(UM_ID), 
            PRIMARY KEY (UM_ID)
);
    
CREATE TABLE UM_CLAIM_BEHAVIOR(
            UM_ID INTEGER GENERATED BY DEFAULT AS IDENTITY, 
            UM_PROFILE_ID INTEGER, 
            UM_CLAIM_ID INTEGER, 
            UM_BEHAVIOUR SMALLINT, 
            UM_TENANT_ID INTEGER DEFAULT 0,
            FOREIGN KEY(UM_PROFILE_ID) REFERENCES UM_PROFILE_CONFIG(UM_ID), 
            FOREIGN KEY(UM_CLAIM_ID) REFERENCES UM_CLAIM(UM_ID), 
            PRIMARY KEY (UM_ID)
);

CREATE TABLE HYBRID_ROLE (
             UM_ID INTEGER GENERATED ALWAYS AS IDENTITY,
             UM_ROLE_ID VARCHAR(255) NOT NULL,
             UM_TENANT_ID INTEGER DEFAULT 0,
             PRIMARY KEY (UM_ID),
             UNIQUE(UM_ROLE_ID)
);

CREATE TABLE HYBRID_USER_ROLE (
             UM_ID INTEGER GENERATED ALWAYS AS IDENTITY,
             UM_USER_ID VARCHAR(255),
             UM_ROLE_ID VARCHAR(255) NOT NULL,
             UM_TENANT_ID INTEGER DEFAULT 0,
             PRIMARY KEY (UM_ID)
);

CREATE TABLE HYBRID_PERMISSION (
             UM_ID INTEGER GENERATED ALWAYS AS IDENTITY,
             UM_RESOURCE_ID VARCHAR(255),
             UM_ACTION VARCHAR(255) NOT NULL,
             UM_TENANT_ID INTEGER DEFAULT 0,
             PRIMARY KEY (UM_ID)
);

CREATE TABLE HYBRID_ROLE_PERMISSION (
             UM_ID INTEGER GENERATED ALWAYS AS IDENTITY,
             UM_PERMISSION_ID INTEGER NOT NULL,
             UM_ROLE_ID VARCHAR(255) NOT NULL,
             UM_IS_ALLOWED SMALLINT NOT NULL,
             UM_TENANT_ID INTEGER DEFAULT 0,
             UNIQUE (UM_PERMISSION_ID, UM_ROLE_ID),
             FOREIGN KEY (UM_PERMISSION_ID) REFERENCES HYBRID_PERMISSION(UM_ID),
             PRIMARY KEY (UM_ID)
);

CREATE TABLE HYBRID_USER_PERMISSION (
             UM_ID INTEGER GENERATED ALWAYS AS IDENTITY,
             UM_PERMISSION_ID INTEGER NOT NULL,
             UM_USER_ID VARCHAR(255) NOT NULL,
             UM_IS_ALLOWED SMALLINT NOT NULL,
             UM_TENANT_ID INTEGER DEFAULT 0,
             UNIQUE (UM_PERMISSION_ID, UM_USER_ID),
             FOREIGN KEY (UM_PERMISSION_ID) REFERENCES HYBRID_PERMISSION(UM_ID),
             PRIMARY KEY (UM_ID)
);
