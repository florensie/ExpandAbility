package be.florens.swimmies;

import com.google.common.reflect.AbstractInvocationHandler;
import me.shedaniel.architectury.event.Event;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static me.shedaniel.architectury.event.EventFactory.of;

/**
 * Provides some alternatives to the event factories in {@link me.shedaniel.architectury.event.EventFactory}
 */
public class EventFactory {

	/**
	 * Like {@link me.shedaniel.architectury.event.EventFactory#createInteractionResult(Object[])} but for booleans
	 */
	@SuppressWarnings("unchecked")
	@SafeVarargs
	public static <T> Event<T> createBoolean(T... typeGetter) {
		if (typeGetter.length != 0) throw new IllegalStateException("array must be empty!");
		return createBoolean((Class<T>) typeGetter.getClass().getComponentType());
	}

	/**
	 * Like {@link me.shedaniel.architectury.event.EventFactory#createInteractionResult(Class)} but for booleans
	 */
	@SuppressWarnings({"unchecked", "UnstableApiUsage"})
	public static <T> Event<T> createBoolean(Class<T> clazz) {
		return of(listeners -> (T) Proxy.newProxyInstance(me.shedaniel.architectury.event.EventFactory.class.getClassLoader(), new Class[]{clazz}, new AbstractInvocationHandler() {
			@Override
			protected Object handleInvocation(@NotNull Object proxy, @NotNull Method method, Object @NotNull [] args) throws Throwable {
				for (T listener : listeners) {
					if ((boolean) method.invoke(listener, args)) {
						return true;
					}
				}
				return false;
			}
		}));
	}
}
