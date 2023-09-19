package br.com.munif.framework.vicente.domain.typings;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;

public class VicSocialUserType implements CompositeUserType {
    @Override
    public String[] getPropertyNames() {
        return new String[]{
                "instagramId",
                "linkedinId",
                "facebookId",
                "twitterId"
        };
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{
                StringType.INSTANCE,
                StringType.INSTANCE,
                StringType.INSTANCE,
                StringType.INSTANCE
        };
    }

    @Override
    public Object getPropertyValue(Object component, int i) throws HibernateException {
        switch (i) {
            case 0:
                return ((VicSocial) component).getInstagramId();
            case 1:
                return ((VicSocial) component).getLinkedinId();
            case 2:
                return ((VicSocial) component).getFacebookId();
            case 3:
                return ((VicSocial) component).getTwitterId();
            default:
                return null;

        }
    }

    @Override
    public void setPropertyValue(Object component, int property, Object setValue) throws HibernateException {
        switch (property) {
            case 0:
                ((VicSocial) component).setInstagramId((String) setValue);
                break;
            case 1:
                ((VicSocial) component).setLinkedinId((String) setValue);
            case 2:
                ((VicSocial) component).setFacebookId((String) setValue);
                break;
            case 3:
                ((VicSocial) component).setTwitterId((String) setValue);
                break;
        }
    }

    @Override
    public Class returnedClass() {
        return VicSocial.class;
    }

    @Override
    public boolean equals(Object o1, Object o2) throws HibernateException {
        boolean isEqual = false;
        if (o1 == o2) {
            isEqual = false;
        }
        if (null == o1 || null == o2) {
            isEqual = false;
        } else {
            isEqual = o1.equals(o2);
        }
        return isEqual;
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        try {
            return new VicSocial(resultSet.getString(names[0]), resultSet.getString(names[1]), resultSet.getString(names[2]), resultSet.getString(names[3]));
        } catch (IndexOutOfBoundsException ignored) {
        }
        return new VicSocial();
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int property, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (null == value) {
            preparedStatement.setNull(property + 0, java.sql.Types.VARCHAR);
            preparedStatement.setNull(property + 1, java.sql.Types.VARCHAR);
            preparedStatement.setNull(property + 2, java.sql.Types.VARCHAR);
            preparedStatement.setNull(property + 3, java.sql.Types.VARCHAR);
        } else {
            final VicSocial object = (VicSocial) value;
            preparedStatement.setString(property + 0, Optional.ofNullable(object.getInstagramId()).orElse(""));
            preparedStatement.setString(property + 1, Optional.ofNullable(object.getLinkedinId()).orElse(""));
            preparedStatement.setString(property + 2, Optional.ofNullable(object.getFacebookId()).orElse(""));
            preparedStatement.setString(property + 3, Optional.ofNullable(object.getTwitterId()).orElse(""));


        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null) {
            return null;
        }

        final VicSocial copy = (VicSocial) value;
        return new VicSocial(copy);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object o, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException {
        return (Serializable) o;
    }

    @Override
    public Object assemble(Serializable cached, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object o1, SharedSessionContractImplementor sharedSessionContractImplementor, Object o2) throws HibernateException {
        return this.deepCopy(original);
    }
}
