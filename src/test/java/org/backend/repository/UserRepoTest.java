package org.backend.repository;

import org.backend.exception.Exceptions;
import org.backend.exception.UserAlreadyExistExceptionMapper;
import org.backend.modals.Personne;
import org.backend.modals.SimpleUser;
import org.backend.modals.UserType;
import org.backend.repository.qualifiers.SimpleUserQualifier;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

@RunWith(Arquillian.class)
public class UserRepoTest {
    @Deployment(testable = false)
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(AbstractRepo.class)
                .addClass(AbstractRepoInt.class)
                .addClass(UserType.class)
                .addClass(Exceptions.class)
                .addClass(UserAlreadyExistExceptionMapper.class)
                .addClass(UserRepo.class)
                .addClass(UserRepoInt.class)
                .addClass(SimpleUser.class)
                .addClass(SimpleUserQualifier.class)
                .addClass(Personne.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject @SimpleUserQualifier
    UserRepo userRepo;
    @Test
    public void create() {
        SimpleUser simpleUser = new SimpleUser();
        simpleUser.setLogin("anas");
        SimpleUser user = userRepo.create(simpleUser);
        Assert.assertNotNull(user);
    }
}
