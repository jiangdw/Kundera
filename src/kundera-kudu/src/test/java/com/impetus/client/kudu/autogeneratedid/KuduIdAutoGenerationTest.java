/*******************************************************************************
 * * Copyright 2017 Impetus Infotech.
 * *
 * * Licensed under the Apache License, Version 2.0 (the "License");
 * * you may not use this file except in compliance with the License.
 * * You may obtain a copy of the License at
 * *
 * * http://www.apache.org/licenses/LICENSE-2.0
 * *
 * * Unless required by applicable law or agreed to in writing, software
 * * distributed under the License is distributed on an "AS IS" BASIS,
 * * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * * See the License for the specific language governing permissions and
 * * limitations under the License.
 ******************************************************************************/
package com.impetus.client.kudu.autogeneratedid;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;

/**
 * The Class KuduIdAutoGenerationTest.
 * 
 * @author karthikp.manchala
 */
public class KuduIdAutoGenerationTest
{

    /** The Constant KUDU_PU. */
    private static final String KUDU_PU = "kudu-auto-gen-pu";

    /** The emf. */
    private static EntityManagerFactory emf;

    /** The em. */
    private EntityManager em;

    /**
     * Sets the up before class.
     * 
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        emf = Persistence.createEntityManagerFactory(KUDU_PU);
    }

    /**
     * Setup.
     */
    @Before
    public void setup()
    {

        em = emf.createEntityManager();
    }

    /**
     * Test.
     * 
     * @throws InterruptedException
     *             the interrupted exception
     */
    @Test
    public void test() throws InterruptedException
    {

        Employee emp1 = new Employee();
        emp1.setName("dev");
        emp1.setSalary(50000);

        Employee emp2 = new Employee();
        emp2.setName("karthik");
        emp2.setSalary(60000);

        em.persist(emp1);
        em.persist(emp2);
        em.clear();

        List<Employee> employees = em.createQuery("select e from Employee e").getResultList();
        Assert.assertEquals(2, employees.size());
        assertEmployees(employees);

        for (Employee emp : employees)
        {
            em.remove(emp);
        }

        employees = em.createQuery("select e from Employee e").getResultList();
        Assert.assertEquals(0, employees.size());

    }

    /**
     * Assert employees.
     * 
     * @param employees
     *            the employees
     */
    private void assertEmployees(List<Employee> employees)
    {

        for (Employee e : employees)
        {
            if (e.getName().equals("dev"))
            {
                Assert.assertEquals(new Integer(50000), e.getSalary());
            }
            else if (e.getName().equals("karthik"))
            {
                Assert.assertEquals(new Integer(60000), e.getSalary());
            }
        }

    }

    /**
     * Tear down.
     * 
     * @throws Exception
     *             the exception
     */
    @After
    public void tearDown() throws Exception
    {
        em.close();
    }

    /**
     * Tear down after class.
     * 
     * @throws Exception
     *             the exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
        if (emf != null)
        {
            emf.close();
        }
    }

}
