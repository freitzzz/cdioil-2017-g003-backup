package eapli.demoapplication.domain.cafeteria;

import eapli.demoapplication.domain.authz.SystemUser;
import eapli.framework.domain.AggregateRoot;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Cafeteria User.
 * <p>
 * This class represents demoapplication users. It follows a DDD approach where User
 * is the root entity of the Cafeteria User Aggregate and all of its properties
 * are instances of value objects. A Cafeteria User references a System User
 * <p>
 * This approach may seem a little more complex than just having String or
 * native type attributes but provides for real semantic of the domain and
 * follows the Single Responsibility Pattern
 *
 * @author Jorge Santos ajs@isep.ipp.pt
 */
@Entity
public class CafeteriaUser implements AggregateRoot<MecanographicNumber>, Serializable {

	private static final long serialVersionUID = 1L;

	@Version
	private Long version;

	@EmbeddedId
	private MecanographicNumber mecanographicNumber;

	@OneToOne(cascade = CascadeType.MERGE)
	private SystemUser systemUser;

	@ManyToOne(cascade = CascadeType.MERGE)
	private OrganicUnit organicUnit;

	public CafeteriaUser(SystemUser user, OrganicUnit organicUnit, String mecanographicNumber) {
		this(user, organicUnit, new MecanographicNumber(mecanographicNumber));
	}

	public CafeteriaUser(SystemUser user, OrganicUnit organicUnit, MecanographicNumber mecanographicNumber) {
		if (mecanographicNumber == null || user == null || organicUnit == null) {
			throw new IllegalStateException();
		}
		this.systemUser = user;
		this.organicUnit = organicUnit;
		this.mecanographicNumber = mecanographicNumber;
	}

	protected CafeteriaUser() {
		// for ORM only
	}

	public SystemUser user() {
		return this.systemUser;
	}

	@Override
	public int hashCode() {
		return this.mecanographicNumber.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CafeteriaUser)) {
			return false;
		}

		final CafeteriaUser other = (CafeteriaUser) o;
		return this.mecanographicNumber.equals(other.mecanographicNumber);
	}

	@Override
	public boolean sameAs(Object other) {
		if (!(other instanceof CafeteriaUser)) {
			return false;
		}

		final CafeteriaUser that = (CafeteriaUser) other;
		if (this == that) {
			return true;
		}
		return (this.mecanographicNumber.equals(that.mecanographicNumber) && this.systemUser.equals(that.systemUser)
				&& this.organicUnit.equals(that.organicUnit));
	}

	@Override
	public boolean is(MecanographicNumber id) {
		return id().equals(id);
	}

	@Override
	public MecanographicNumber id() {
		return this.mecanographicNumber;
	}

	public MecanographicNumber mecanographicNumber() {
		return id();
	}

	public OrganicUnit organicUnit() {
		return this.organicUnit;
	}
}
