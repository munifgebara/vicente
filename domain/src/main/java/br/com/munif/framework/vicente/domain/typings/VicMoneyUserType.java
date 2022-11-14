package br.com.munif.framework.vicente.domain.typings;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;

public class VicMoneyUserType implements CompositeUserType {
    @Override
    public String[] getPropertyNames() {
        return new String[]{
                "amount",
                "locale",
                "recurring"
        };
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{
                BigDecimalType.INSTANCE,
                StringType.INSTANCE,
                StringType.INSTANCE
        };
    }

    @Override
    public Object getPropertyValue(Object component, int i) throws HibernateException {
        switch (i) {
            case 0:
                return ((VicMoney) component).getAmount();
            case 1:
                return ((VicMoney) component).getLocale();
            case 2:
                return ((VicMoney) component).getRecurring();
            default:
                return null;

        }
    }

    @Override
    public void setPropertyValue(Object component, int property, Object setValue) throws HibernateException {
        switch (property) {
            case 0:
                ((VicMoney) component).setAmount((BigDecimal) setValue);
                break;
            case 1:
                ((VicMoney) component).setLocale((String) setValue);
                break;
            case 2:
                ((VicMoney) component).setRecurring(
                        setValue != null ? VicRecurring.valueOf(String.valueOf(setValue)) : VicRecurring.NONE);
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
    public Object nullSafeGet(ResultSet resultSet, String[] names,
                              SharedSessionContractImplementor sharedSessionContractImplementor, Object o)
            throws HibernateException, SQLException {
        final BigDecimal description = resultSet.getBigDecimal(names[0]);
        for (int i = 0; i < names.length; i++) {
            if (resultSet.getObject(names[i]) != null) {
                return new VicMoney(description, String.valueOf(resultSet.getString(names[i + 1])),
                        VicRecurring.valueOf(resultSet.getString(names[i + 2])));
            }
        }
        return new VicMoney();
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int property,
                            SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (null == value) {
            preparedStatement.setNull(property + 0, Types.BIGINT);
            preparedStatement.setNull(property + 1, java.sql.Types.VARCHAR);
            preparedStatement.setNull(property + 2, java.sql.Types.VARCHAR);
        } else {
            final VicMoney object = (VicMoney) value;
            preparedStatement.setBigDecimal(property + 0, object.getAmount());
            preparedStatement.setString(property + 1,
                    Optional.ofNullable(object.getLocale()).orElse("pt-BR"));
            preparedStatement.setString(property + 2,
                    Optional.ofNullable(object.getRecurring()).orElse(VicRecurring.NONE).name());

        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null) {
            return null;
        }
        final VicMoney recepty = (VicMoney) value;
        return new VicMoney(recepty);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object o, SharedSessionContractImplementor sharedSessionContractImplementor)
            throws HibernateException {
        return (Serializable) o;
    }

    @Override
    public Object assemble(Serializable cached, SharedSessionContractImplementor sharedSessionContractImplementor,
                           Object o) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object o1, SharedSessionContractImplementor sharedSessionContractImplementor,
                          Object o2) throws HibernateException {
        return this.deepCopy(original);
    }
}
