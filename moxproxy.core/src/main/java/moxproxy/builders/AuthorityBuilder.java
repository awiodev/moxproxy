package moxproxy.builders;

import moxproxy.exceptions.BuilderValidationException;
import org.littleshoot.proxy.mitm.Authority;

import java.io.File;

public class AuthorityBuilder extends BaseBuilder<LocalMoxProxy, AuthorityBuilder, Authority, AuthorityBuilderValidator> {

    private String keyStoreDir = ".";
    private String alias = "moxproxy-mitm";
    private String password = "doItOnlyForTesting";
    private String organization = "MoxProxy-mitm";
    private String commonName = "MoxProxy-mitm, test proxy";
    private String organizationalUnitName = "Certificate Authority";
    private String certOrganization = "MoxProxy-mitm";
    private String certOrganizationalUnitName = "MoxProxy-mitm, test automation purpose";

    AuthorityBuilder(LocalMoxProxy builder) {
        super(builder, new AuthorityBuilderValidator());
    }

    @Override
    protected Authority performBuild() throws BuilderValidationException {
        return new Authority(new File(keyStoreDir), alias, password.toCharArray(), organization, commonName, organizationalUnitName, certOrganization, certOrganizationalUnitName);
    }

    @Override
    protected AuthorityBuilder getCurrentBuilder() {
        return this;
    }

    public AuthorityBuilder withKeyStoreDir(String keyStoreDir){
        this.keyStoreDir = keyStoreDir;
        return this;
    }

    public AuthorityBuilder withAlias(String alias){
        this.alias = alias;
        return this;
    }

    public AuthorityBuilder withPassword(String password){
        this.password = password;
        return this;
    }

    public AuthorityBuilder withOrganization(String organization){
        this.organization = organization;
        return this;
    }

    public AuthorityBuilder withCommonName(String commonName){
        this.commonName = commonName;
        return this;
    }

    public AuthorityBuilder withOrganizationalUnitName(String organizationalUnitName){
        this.organizationalUnitName = organizationalUnitName;
        return this;
    }

    public AuthorityBuilder withCertOrganization(String certOrganization){
        this.certOrganization = certOrganization;
        return this;
    }

    public AuthorityBuilder withCertOrganizationalUnitName(String certOrganizationalUnitName){
        this.certOrganizationalUnitName = certOrganizationalUnitName;
        return this;
    }

    public static AuthorityBuilder create(){
        return new AuthorityBuilder(null);
    }
}
