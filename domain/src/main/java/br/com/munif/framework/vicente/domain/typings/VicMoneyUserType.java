package br.com.munif.framework.vicente.domain.typings;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class VicMoneyUserType implements CompositeUserType {
    @Override
    public String[] getPropertyNames() {
        return new String[]{
                "amount",
                "currencyType"};
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{
                LongType.INSTANCE,
                StringType.INSTANCE};
    }

    @Override
    public Object getPropertyValue(Object component, int i) throws HibernateException {
        switch (i) {
            case 0:
                return ((VicMoney) component).getAmount();
            case 1:
                return ((VicMoney) component).getCurrencyType();
            default:
                return null;

        }
    }

    @Override
    public void setPropertyValue(Object component, int property, Object setValue) throws HibernateException {
        switch (property) {
            case 0:
                ((VicMoney) component).setAmount((Long) setValue);
                break;
            case 1:
                ((VicMoney) component).setCurrencyType((String) setValue);
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
        final Long description = Long.valueOf(resultSet.getString(names[0]));
        for (int i = 0; i < names.length; i++) {
            if (resultSet.getObject(names[i]) != null) {
                return new VicMoney(description, VicCurrencyType.valueOf(resultSet.getString(names[i])));
            }
        }
        return new VicMoney();
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int property, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (null == value) {
            preparedStatement.setNull(property, java.sql.Types.BIGINT);
            preparedStatement.setNull(property + 1, java.sql.Types.VARCHAR);
        } else {
            final VicMoney object = (VicMoney) value;
            preparedStatement.setLong(property, object.getAmount());
            preparedStatement.setString(property + 1, String.valueOf(Optional.ofNullable(object.getCurrencyType()).orElse(VicCurrencyType.BRL)));

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
