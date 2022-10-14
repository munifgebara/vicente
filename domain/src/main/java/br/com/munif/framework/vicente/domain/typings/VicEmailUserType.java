package br.com.munif.framework.vicente.domain.typings;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.BooleanType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class VicEmailUserType implements CompositeUserType {
    @Override
    public String[] getPropertyNames() {
        return new String[]{
                "description",
                "type",
                "invalidEmail",
                "invalidEmailReason"
        };
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{
                StringType.INSTANCE,
                StringType.INSTANCE,
                BooleanType.INSTANCE,
                StringType.INSTANCE
        };
    }

    @Override
    public Object getPropertyValue(Object component, int i) throws HibernateException {
        switch (i) {
            case 0:
                return ((VicEmail) component).getDescription();
            case 1:
                return ((VicEmail) component).getType();
            case 2:
                return ((VicEmail) component).getInvalidEmail();
            case 3:
                return ((VicEmail) component).getInvalidEmailReason();
            default:
                return null;

        }
    }

    @Override
    public void setPropertyValue(Object component, int property, Object setValue) throws HibernateException {
        switch (property) {
            case 0:
                ((VicEmail) component).setDescription((String) setValue);
                break;
            case 1:
                ((VicEmail) component).setType(SocialNetworking.valueOf((String) setValue));
                break;
            case 2:
                ((VicEmail) component).setInvalidEmail((Boolean) setValue);
                break;
            case 3:
                ((VicEmail) component).setInvalidEmailReason((String) setValue);
                break;
        }
    }

    @Override
    public Class returnedClass() {
        return VicAddress.class;
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
        final String description = resultSet.getString(names[0]);
        final String type = resultSet.getString(names[1]);
        final Boolean invalidEmail = resultSet.getBoolean(names[2]);
        final String invalidEmailReason = resultSet.getString(names[3]);
        return new VicEmail(description, type, invalidEmail, invalidEmailReason);
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int property, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (null == value) {
            preparedStatement.setNull(property + 0, java.sql.Types.VARCHAR);
            preparedStatement.setNull(property + 1, java.sql.Types.VARCHAR);
            preparedStatement.setNull(property + 2, java.sql.Types.BOOLEAN);
            preparedStatement.setNull(property + 3, java.sql.Types.VARCHAR);
        } else {
            final VicEmail object = (VicEmail) value;
            preparedStatement.setString(property + 0, object.getDescription());
            preparedStatement.setString(property + 1, Optional.ofNullable(object.getType()).orElse(SocialNetworking.EMAIL).name());
            preparedStatement.setBoolean(property + 2, object.getInvalidEmail());
            preparedStatement.setString(property + 3, object.getInvalidEmailReason());

        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null) {
            return null;
        }

        final VicEmail recepty = (VicEmail) value;
        return new VicEmail(recepty);
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
