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
import java.util.Optional;

public class VicPhoneUserType implements CompositeUserType {
    @Override
    public String[] getPropertyNames() {
        return new String[]{
                "description",
                "type",
                "countryCode",
                "regionCode"
        };
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{
                StringType.INSTANCE,
                StringType.INSTANCE,
                IntegerType.INSTANCE,
                StringType.INSTANCE
        };
    }

    @Override
    public Object getPropertyValue(Object component, int i) throws HibernateException {
        switch (i) {
            case 0:
                return ((VicPhone) component).getDescription();
            case 1:
                return ((VicPhone) component).getType();
            case 2:
                return ((VicPhone) component).getCountryCode();
            case 3:
                return ((VicPhone) component).getRegionCode();
            default:
                return null;

        }
    }

    @Override
    public void setPropertyValue(Object component, int property, Object setValue) throws HibernateException {
        switch (property) {
            case 0:
                ((VicPhone) component).setDescription((String) setValue);
                break;
            case 1:
                ((VicPhone) component).setType(PhoneType.valueOf((String) setValue));
            case 2:
                ((VicPhone) component).setCountryCode((Integer) setValue);
                break;
            case 3:
                ((VicPhone) component).setRegionCode((String) setValue);
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
        try {
            return new VicPhone(resultSet.getString(names[0]), resultSet.getString(names[1]), resultSet.getInt(names[2]), resultSet.getString(names[3]));
        } catch (IndexOutOfBoundsException ignored) {
        }
        return new VicPhone();
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int property, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (null == value) {
            preparedStatement.setNull(property + 0, java.sql.Types.VARCHAR);
            preparedStatement.setNull(property + 1, java.sql.Types.VARCHAR);
            preparedStatement.setNull(property + 2, java.sql.Types.INTEGER);
            preparedStatement.setNull(property + 3, java.sql.Types.VARCHAR);
        } else {
            final VicPhone object = (VicPhone) value;
            preparedStatement.setString(property + 0, object.getDescription());
            preparedStatement.setString(property + 1, Optional.ofNullable(object.getType()).orElse(PhoneType.CELLPHONE).name());
            preparedStatement.setInt(property + 2, Optional.ofNullable(object.getCountryCode()).orElse(55));
            preparedStatement.setString(property + 3, Optional.ofNullable(object.getRegionCode()).orElse("BR"));

        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null) {
            return null;
        }

        final VicPhone received = (VicPhone) value;
        return new VicPhone(received);
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
