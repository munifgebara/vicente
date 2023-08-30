package br.com.munif.framework.vicente.domain.typings;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.DoubleType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VicAddressUserType implements CompositeUserType {
    @Override
    public String[] getPropertyNames() {
        return new String[]{
                "zipCode",
                "premiseType",
                "premise",
                "number",
                "information",
                "neighbourhood",
                "localization",
                "state",
                "country",
                "latitude",
                "longitude",
                "formalCode",
                "stateCode"};
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{
                StringType.INSTANCE,
                StringType.INSTANCE,
                StringType.INSTANCE,
                StringType.INSTANCE,
                StringType.INSTANCE,
                StringType.INSTANCE,
                StringType.INSTANCE,
                StringType.INSTANCE,
                StringType.INSTANCE,
                DoubleType.INSTANCE,
                DoubleType.INSTANCE,
                StringType.INSTANCE,
                StringType.INSTANCE};
    }

    @Override
    public Object getPropertyValue(Object component, int i) throws HibernateException {
        switch (i) {
            case 0:
                return ((VicAddress) component).getZipCode();
            case 1:
                return ((VicAddress) component).getPremiseType();
            case 2:
                return ((VicAddress) component).getPremise();
            case 3:
                return ((VicAddress) component).getNumber();
            case 4:
                return ((VicAddress) component).getInformation();
            case 5:
                return ((VicAddress) component).getNeighbourhood();
            case 6:
                return ((VicAddress) component).getLocalization();
            case 7:
                return ((VicAddress) component).getState();
            case 8:
                return ((VicAddress) component).getCountry();
            case 9:
                return ((VicAddress) component).getLatitude();
            case 10:
                return ((VicAddress) component).getLongitude();
            case 11:
                return ((VicAddress) component).getFormalCode();
            case 12:
                return ((VicAddress) component).getStateCode();
            default:
                return null;

        }
    }

    @Override
    public void setPropertyValue(Object component, int property, Object setValue) throws HibernateException {
        switch (property) {
            case 0:
                ((VicAddress) component).setZipCode((String) setValue);
                break;
            case 1:
                ((VicAddress) component).setPremiseType((String) setValue);
                break;
            case 2:
                ((VicAddress) component).setPremise((String) setValue);
                break;
            case 3:
                ((VicAddress) component).setNumber((String) setValue);
                break;
            case 4:
                ((VicAddress) component).setInformation((String) setValue);
                break;
            case 5:
                ((VicAddress) component).setNeighbourhood((String) setValue);
                break;
            case 6:
                ((VicAddress) component).setLocalization((String) setValue);
                break;
            case 7:
                ((VicAddress) component).setState((String) setValue);
                break;
            case 8:
                ((VicAddress) component).setCountry((String) setValue);
                break;
            case 9:
                ((VicAddress) component).setLatitude((Double) setValue);
                break;
            case 10:
                ((VicAddress) component).setLongitude((Double) setValue);
                break;
            case 11:
                ((VicAddress) component).setFormalCode((String) setValue);
                break;
            case 12:
                ((VicAddress) component).setStateCode((String) setValue);
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
        //owner here is of type TestUser or the actual owning Object
        VicAddress object = null;
        final String zipCode = resultSet.getString(names[0]);
        //Deferred check after first read
        for (int i = 0; i < names.length; i++) {
            if (resultSet.getObject(names[i]) != null) {
                return new VicAddress(zipCode, resultSet.getString(names[1]), resultSet.getString(names[2]), resultSet.getString(names[3]), resultSet.getString(names[4]),
                        resultSet.getString(names[5]), resultSet.getString(names[6]), resultSet.getString(names[7]), resultSet.getString(names[8]),
                        resultSet.getDouble(names[9]), resultSet.getDouble(names[10]), resultSet.getString(names[11]), resultSet.getString(names[12]));
            }
        }
        return new VicAddress();
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int property, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (null == value) {
            preparedStatement.setNull(property + 0, java.sql.Types.VARCHAR);
            preparedStatement.setNull(property + 1, java.sql.Types.VARCHAR);
            preparedStatement.setNull(property + 2, java.sql.Types.VARCHAR);
            preparedStatement.setNull(property + 3, java.sql.Types.VARCHAR);
            preparedStatement.setNull(property + 4, java.sql.Types.VARCHAR);
            preparedStatement.setNull(property + 5, java.sql.Types.VARCHAR);
            preparedStatement.setNull(property + 6, java.sql.Types.VARCHAR);
            preparedStatement.setNull(property + 7, java.sql.Types.VARCHAR);
            preparedStatement.setNull(property + 8, java.sql.Types.VARCHAR);
            preparedStatement.setNull(property + 9, java.sql.Types.DOUBLE);
            preparedStatement.setNull(property + 10, java.sql.Types.DOUBLE);
            preparedStatement.setNull(property + 11, java.sql.Types.VARCHAR);
            preparedStatement.setNull(property + 12, java.sql.Types.VARCHAR);
        } else {
            final VicAddress object = (VicAddress) value;
            preparedStatement.setString(property + 0, object.getZipCode());
            preparedStatement.setString(property + 1, object.getPremiseType());
            preparedStatement.setString(property + 2, object.getPremise());
            preparedStatement.setString(property + 3, object.getNumber());
            preparedStatement.setString(property + 4, object.getInformation());
            preparedStatement.setString(property + 5, object.getNeighbourhood());
            preparedStatement.setString(property + 6, object.getLocalization());
            preparedStatement.setString(property + 7, object.getState());
            preparedStatement.setString(property + 8, object.getCountry());

            if (object.getLatitude() != null) {
                preparedStatement.setDouble(property + 9, object.getLatitude());
            } else {
                preparedStatement.setNull(property + 9, java.sql.Types.DOUBLE);
            }

            if (object.getLongitude() != null) {
                preparedStatement.setDouble(property + 10, object.getLongitude());
            } else {
                preparedStatement.setNull(property + 10, java.sql.Types.DOUBLE);
            }

            preparedStatement.setString(property + 11, object.getFormalCode());
            preparedStatement.setString(property + 12, object.getStateCode());

        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null) {
            return null;
        }

        final VicAddress recepty = (VicAddress) value;
        final VicAddress toReturn = new VicAddress(recepty);
        return toReturn;
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
