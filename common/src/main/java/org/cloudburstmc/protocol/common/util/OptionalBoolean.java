package org.cloudburstmc.protocol.common.util;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;

import java.util.NoSuchElementException;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * A container object which may or may not contain a {@code boolean} value.
 * If a value is present, {@code isPresent()} will return {@code true} and
 * {@code getAsBoolean()} will return the value.
 *
 * <p>Additional methods that depend on the presence or absence of a contained
 * value are provided, such as {@link #orElse(boolean) orElse()}
 * (return a default value if value not present) and
 * {@link #ifPresent(BooleanConsumer) ifPresent()} (execute a block
 * of code if the value is present).
 *
 * <p>This is a <a href="../lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code OptionalBoolean} may have unpredictable results and should be avoided.
 *
 * @since 1.8
 */
public final class OptionalBoolean {

    /**
     * Common instance for {@code empty()}.
     */
    private static final OptionalBoolean EMPTY = new OptionalBoolean();

    /**
     * If true then the value is present, otherwise indicates no value is present
     */
    private final boolean isPresent;
    private final boolean value;

    /**
     * Construct an empty instance.
     *
     * @implNote generally only one empty instance, {@link OptionalBoolean#EMPTY},
     * should exist per VM.
     */
    private OptionalBoolean() {
        this.isPresent = false;
        this.value = false;
    }

    /**
     * Returns an empty {@code OptionalBoolean} instance.  No value is present for this
     * OptionalBoolean.
     *
     * Though it may be tempting to do so, avoid testing if an object
     * is empty by comparing with {@code ==} against instances returned by
     * {@code Option.empty()}. There is no guarantee that it is a singleton.
     * Instead, use {@link #isPresent()}.
     *
     *  @return an empty {@code OptionalBoolean}.
     */
    public static OptionalBoolean empty() {
        return EMPTY;
    }

    /**
     * Construct an instance with the value present.
     *
     * @param value the boolean value to be present.
     */
    private OptionalBoolean(boolean value) {
        this.isPresent = true;
        this.value = value;
    }

    /**
     * Return an {@code OptionalBoolean} with the specified value present.
     *
     * @param value the value to be present
     * @return an {@code OptionalBoolean} with the value present
     */
    public static OptionalBoolean of(boolean value) {
        return new OptionalBoolean(value);
    }

    /**
     * If a value is present in this {@code OptionalBoolean}, returns the value,
     * otherwise throws {@code NoSuchElementException}.
     *
     * @return the value held by this {@code OptionalBoolean}
     * @throws NoSuchElementException if there is no value present
     *
     * @see OptionalBoolean#isPresent()
     */
    public boolean getAsBoolean() {
        if (!isPresent) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    /**
     * Return {@code true} if there is a value present, otherwise {@code false}.
     *
     * @return {@code true} if there is a value present, otherwise {@code false}
     */
    public boolean isPresent() {
        return isPresent;
    }

    /**
     * Have the specified consumer accept the value if a value is present,
     * otherwise do nothing.
     *
     * @param consumer block to be executed if a value is present
     * @throws NullPointerException if value is present and {@code consumer} is
     * null
     */
    public void ifPresent(BooleanConsumer consumer) {
        if (isPresent)
            consumer.accept(value);
    }

    /**
     * Return the value if present, otherwise return {@code other}.
     *
     * @param other the value to be returned if there is no value present
     * @return the value, if present, otherwise {@code other}
     */
    public boolean orElse(boolean other) {
        return isPresent ? value : other;
    }

    /**
     * Return the value if present, otherwise invoke {@code other} and return
     * the result of that invocation.
     *
     * @param other a {@code BooleanSupplier} whose result is returned if no value
     * is present
     * @return the value if present otherwise the result of {@code other.getAsBoolean()}
     * @throws NullPointerException if value is not present and {@code other} is
     * null
     */
    public boolean orElseGet(BooleanSupplier other) {
        return isPresent ? value : other.getAsBoolean();
    }

    /**
     * Return the contained value, if present, otherwise throw an exception
     * to be created by the provided supplier.
     *
     * A method reference to the exception constructor with an empty
     * argument list can be used as the supplier. For example,
     * {@code IllegalStateException::new}
     *
     * @param <X> Type of the exception to be thrown
     * @param exceptionSupplier The supplier which will return the exception to
     * be thrown
     * @return the present value
     * @throws X if there is no value present
     * @throws NullPointerException if no value is present and
     * {@code exceptionSupplier} is null
     */
    public<X extends Throwable> boolean orElseThrow(Supplier<X> exceptionSupplier) throws X {
        if (isPresent) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    /**
     * Indicates whether some other object is "equal to" this OptionalBoolean. The
     * other object is considered equal if:
     * <ul>
     * <li>it is also an {@code OptionalBoolean} and;
     * <li>both instances have no value present or;
     * <li>the present values are "equal to" each other via {@code Boolean.compare() == 0}.
     * </ul>
     *
     * @param obj an object to be tested for equality
     * @return {code true} if the other object is "equal to" this object
     * otherwise {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof OptionalBoolean)) {
            return false;
        }

        OptionalBoolean other = (OptionalBoolean) obj;
        return (isPresent && other.isPresent)
                ? Boolean.compare(value, other.value) == 0
                : isPresent == other.isPresent;
    }

    /**
     * Returns the hash code value of the present value, if any, or 0 (zero) if
     * no value is present.
     *
     * @return hash code value of the present value or 0 if no value is present
     */
    @Override
    public int hashCode() {
        return isPresent ? Boolean.hashCode(value) : 0;
    }

    /**
     * {@inheritDoc}
     *
     * Returns a non-empty string representation of this object suitable for
     * debugging. The exact presentation format is unspecified and may vary
     * between implementations and versions.
     *
     * If a value is present the result must include its string
     * representation in the result. Empty and present instances must be
     * unambiguously differentiable.
     *
     * @return the string representation of this instance
     */
    @Override
    public String toString() {
        return isPresent
                ? String.format("OptionalBoolean[%s]", value)
                : "OptionalBoolean.empty";
    }
}
