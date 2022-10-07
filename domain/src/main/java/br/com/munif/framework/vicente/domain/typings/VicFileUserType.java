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

public class VicFileUserType implements CompositeUserType {
    @Override
    public String[] getPropertyNames() {
        return new String[]{
                "id",
                "bucket"
        };
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{
                StringType.INSTANCE,
                StringType.INSTANCE,
        };
    }

    @Override
    public Object getPropertyValue(Object component, int i) throws HibernateException {
        switch (i) {
            case 0:
                return ((VicFile) component).getId();
            case 1:
                return ((VicFile) component).getBucket();
            default:
                return null;

        }
    }

    @Override
    public void setPropertyValue(Object component, int property, Object setValue) throws HibernateException {
        switch (property) {
            case 0:
                ((VicFile) component).setId((String) setValue);
                break;
            case 1:
                ((VicFile) component).setBucket((String) setValue);
                break;
        }
    }

    @Override
    public Class returnedClass() {
        return VicAddress.class;
    }

    @Override
    public boolean equals(Object o1, Object o2) throws HibernateException {
        boolean isEqual;
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
            return new VicFile(resultSet.getString(names[0]), resultSet.getString(names[1]));
        } catch (IndexOutOfBoundsException ignored) {
        }
        return new VicFile();
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int property, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (null == value) {
            preparedStatement.setNull(property + 0, java.sql.Types.VARCHAR);
            preparedStatement.setNull(property + 1, java.sql.Types.VARCHAR);
        } else {
            final VicFile object = (VicFile) value;
            preparedStatement.setString(property + 0, object.getId());
            preparedStatement.setString(property + 1, object.getBucket());

        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null) {
            return null;
        }

        final VicFile received = (VicFile) value;
        return new VicFile(received);
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
