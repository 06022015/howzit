package com.howzit.java.service.imp;

import com.howzit.java.service.ImageService;

import javax.imageio.ImageIO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: ashif
 * Date: 10/20/14
 * Time: 11:52 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("/image")
@Produces({"image/png", "image/jpg"})
public class ImageServiceImpl extends BaseServiceImpl implements ImageService {


    public ImageServiceImpl(@Context SecurityContext security) {
        super(security);
    }

    private CacheControl getCacheControl() {
        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(60);
        cacheControl.setPrivate(true);
        return cacheControl;
    }

    @GET
    @Path("/activity/{id}")
    public Response getActivityImage(@PathParam("id") Long id) {
        byte[] imageData = getActivityBL().getActivityImage(id);
        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write((BufferedImage) image, "png", out);
                final byte[] imgData = out.toByteArray();
                final InputStream bigInputStream =
                      new ByteArrayInputStream(imgData);
                return Response.ok(bigInputStream).
                     cacheControl(getCacheControl()).build();
        }catch (Exception e){
            return Response.noContent().build();
        }
        //return Response.ok(new ByteArrayInputStream(imageData)).cacheControl(getCacheControl()).build();
    }
}
