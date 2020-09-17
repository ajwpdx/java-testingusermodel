package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest
{

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        List<Role> myList = roleService.findAll();
        for (Role r : myList)
        {
            System.out.println(r.getRoleid() + " " + r.getName());
        }
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void a_findUserById()
    {
        assertEquals("barnbarn", userService.findUserById(11).getUsername());
    }


    @Test (expected = ResourceNotFoundException.class)
    public void aa_findUserByIdNotFound()
    {
        assertEquals("", userService.findUserById(55));
    }

    @Test
    public void b_findByNameContaining()
    {
        assertEquals(1, userService.findByNameContaining("cinna").size());
    }

    @Test
    public void c_findAll()
    {
        assertEquals(5, userService.findAll().size());
    }

    @Test
    public void d_delete()
    {
        assertEquals(5, userService.findAll().size());
        userService.delete(14);
        assertEquals(4, userService.findAll().size());
    }

    @Test
    public void e_findByName()
    {
        assertEquals(11, userService.findByName("barnbarn").getUserid());
    }

    @Test (expected = ResourceNotFoundException.class)
    public void ee_findByNameNotFound()
    {
        assertEquals("", userService.findByName("barn"));
    }

    @Test
    public void g_save()
    {
        String u2name = "bailey";
        User u2 = new User(u2name,
                "dogwalk",
                "bailey@lambdaschool.local");

        Role r1 = new Role("user");
        r1.setRoleid(2);

        Role r2 = new Role("data");
        r2.setRoleid(3);

        u2.getRoles()
                .add(new UserRoles(u2, r1));
        u2.getRoles()
                .add(new UserRoles(u2, r2));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "bailey@mymail.local"));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "bark@mymail.local"));

        User addUser = userService.save(u2);

        assertNotNull(addUser);

        assertEquals(u2name, addUser.getUsername());
    }

    @Test
    public void f_update()
    {
        String u7name = "barnbarnbarn";
        User u7 = new User(u7name,
                "bunney",
                "bailey@lambdaschool.local");

        Role r1 = new Role("admin");
        r1.setRoleid(1);

        u7.getRoles()
                .add(new UserRoles(u7, r1));

        u7.getUseremails()
                .add(new Useremail(u7,
                        "bailey@mymail.local"));


        User updateUser = userService.update(u7, 11);

        System.out.println(u7name);
        System.out.println(userService.findUserById(11).getUsername());
        assertEquals(u7name, userService.findUserById(11).getUsername());
    }

    @Test
    public void h_deleteAll()
    {
        userService.deleteAll();
        assertEquals(0, userService.findAll().size());

    }
}