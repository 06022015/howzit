package com.howzit.java.service.imp;

import com.howzit.java.exception.HowItResponseStatus;
import com.howzit.java.service.CategoryService;
import com.howzit.java.service.wso.Categories;
import com.howzit.java.service.wso.Category;
import com.howzit.java.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 9/20/14
 * Time: 1:04 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/category")
@Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
public class CategoryServiceImpl extends BaseServiceImpl implements CategoryService, Constants {


    public CategoryServiceImpl(@Context SecurityContext security) {
        super(security);

    }

    @POST
    @Path("/save")
    public Response saveCategory(@FormParam(PARAM_CATEGORY_NAME)String name,
                                 @FormParam(PARAM_CATEGORY_DESCRIPTION)String description,
                                 @FormParam(PARAM_PARENT_CATEGORY_ID)Long parentCategoryId){
        if(!isUserInRole(ROLE_NAME_ADMIN))
            throw new AccessDeniedException("Only Admin Allow");
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        category.setParentCategoryId(parentCategoryId);
        category.setCreatedBy(getActivityBy());
        HowItResponseStatus status = new HowItResponseStatus();
         category = getCategoryBL().save(category,status);
        return status.getCode()== HttpStatus.OK.value()
                ?Response.ok(category).build(): Response.status(status.getCode()).entity(status).build();
    }

    @GET
    @Path("/show")
    public Response getCategories(){
        Categories categories= getCategoryBL().getCategories();
        return Response.ok(categories).build();
    }

    @GET
    @Path("/show/{id}")
    public Response getCategory(@PathParam("id")Long id){
        return Response.ok(getCategoryBL().getCategory(id)).build();
    }
}
