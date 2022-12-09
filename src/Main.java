package com.raritan.tdz.connector;

import com.raritan.tdz.autopowerbudget.dto.APBItemDTO;
import com.raritan.tdz.connector.home.ConnectorLibHomeImpl;
import com.raritan.tdz.controllers.base.ObjectExceptionBaseController;
import com.raritan.tdz.dto.UiFieldJSON;
import com.raritan.tdz.exception.BusinessValidationException;
import com.raritan.tdz.lookup.home.UserLookupHome;
import com.raritan.tdz.lookup.home.UserLookupHomeHelper;
import com.raritan.tdz.lookup.json.ListContentJSON;
import com.raritan.tdz.session.RESTAPIUserSessionContext;
import org.hibernate.annotations.Proxy;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.SQLException;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertNotNull;

public class ConnectorLibHomeImplIntegrationTest extends ConnectorIntegrationTestBase {


    @Test(groups = "CONNECTOR_INTEGRATION_TESTS")
    public void testGetConnectorList() throws Throwable {
        runTestTemplate("connector/noFilter/allFields");
    }



    private ConnectorLibHomeImpl connectorLibHome;
    private UserLookupHome userLookupHome;

    @BeforeClass(groups = "INTEGRATION_TEST")
    public void beforeClass() throws Exception{
        super.beforeClass();

        connectorLibHome = (ConnectorLibHomeImpl) getObject(applicationContext.getBean("connectorLibHome"));

    }


    @AfterClass(groups = "INTEGRATION_TEST")
    public void afterClass() throws SQLException{
        super.afterClass();

    }

    @Test(groups = "INTEGRATION_TEST")
    public void testDeleteCord() throws BusinessValidationException {
        HttpServletRequest httpServletRequest = null;
        HttpServletResponse httpServletResponse = null;
        //Long id = 850L;

        ListContentJSON listContentJSON = new ListContentJSON();
        listContentJSON.setTiLabel((new UiFieldJSON<>(ListContentJSON.INIT_LABEL, "Patch Cord Test", true)));
        listContentJSON.setCmbColor((new UiFieldJSON<>(ListContentJSON.INIT_COLOR, "Black", true)));
        listContentJSON.setCmbType((new UiFieldJSON<>(ListContentJSON.INIT_CORD_TYPE, "Data", true)));

        ObjectExceptionBaseController.WrappedListContentJSON<ListContentJSON> wrappedListContent = new ObjectExceptionBaseController.WrappedListContentJSON<ListContentJSON>();
        wrappedListContent.setListContent(listContentJSON);

        connectorLibHome.createCord(httpServletRequest, "cord", wrappedListContent, httpServletResponse);


        //connectorLibHome.deleteCord(httpServletRequest,UserLookupHomeHelper.L_ID_CORD, id, false, httpServletResponse);

    }

    public Object getObject(Object o) throws Exception{
        for (PropertyDescriptor pd: Introspector.getBeanInfo(o.getClass()).getPropertyDescriptors()){
            if (pd.getReadMethod() != null && "targetSource".equals(pd.getName()) ){
                Object existing = pd.getReadMethod().invoke(o);
                for (PropertyDescriptor pd2: Introspector.getBeanInfo(existing.getClass()).getPropertyDescriptors()){
                    if (pd2.getReadMethod() != null && "target".equals(pd2.getName()) ){
                        return pd2.getReadMethod().invoke(existing);
                    }
                }
            }
        }
        return null;
    }



}
