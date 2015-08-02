package org.verapdf.model.impl.pb.cos;

import org.apache.log4j.Logger;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSObject;
import org.verapdf.model.GenericModelObject;
import org.verapdf.model.coslayer.CosObject;
import org.verapdf.model.tools.IDGenerator;
import org.verapdf.model.visitor.cos.pb.PBCosVisitor;

import java.io.IOException;

/**
 * Current class is representation of CosObject interface of abstract model.
 * This class is analogue of COSBase in pdfbox.
 *
 * @author Evgeniy Muravitskiy
 */
public class PBCosObject extends GenericModelObject implements CosObject {

    /** Type name for PBCosObject */
    public static final String COS_OBJECT_TYPE = "CosObject";
    private static final Logger LOGGER = Logger.getLogger(PBCosObject.class);

    protected final COSBase baseObject;
    private final String type;
    private final String id;

    PBCosObject(final COSBase baseObject) {
        this(baseObject, COS_OBJECT_TYPE);
    }

    PBCosObject(final COSBase baseObject, final String type) {
        this.baseObject = baseObject;
        this.type = type;
        id = IDGenerator.generateID(baseObject);
    }

    /**
     * Get type of current object
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * Get personal id of current object
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * Transform object of pdf box to corresponding object of abstract model
     * implementation. For transforming using {@code PBCosVisitor}.
     *
     * @param base
     *            the base object that all objects in the PDF document will
     *            extend in pdf box
     * @return object of abstract model implementation, transformed from
     *         {@code base}
     */
    public static CosObject getFromValue(COSBase base) {
        try {
            if (base != null) {
                PBCosVisitor visitor = PBCosVisitor.getInstance();
                if (base instanceof COSObject) {
                    return (CosObject) PBCosVisitor
                            .visitFromObject((COSObject) base);
                }
                return (CosObject) base.accept(visitor);
            }
        } catch (IOException e) {
            LOGGER.error(
                    "Problems with wrapping pdfbox object \"" + base.toString()
                            + "\". " + e.getMessage(), e);
        }
        return null;
    }
}
