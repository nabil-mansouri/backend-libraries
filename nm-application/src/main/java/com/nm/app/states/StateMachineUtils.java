package com.nm.app.states;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineBuilder.Builder;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import com.google.common.base.Objects;

/**
 * 
 * @author nabilmansouri
 *
 */
public class StateMachineUtils {

	public static <S, E> Collection<S> findAllNextStates(StateMachine<S, E> machine, S from) throws Exception {
		Set<S> collectS = new HashSet<S>();
		for (State<S, E> s : machine.getStates()) {
			collectS.add(s.getId());
		}
		//
		Collection<S> founded = new LinkedHashSet<S>();
		for (Transition<S, E> e : machine.getTransitions()) {
			Builder<S, E> b = StateMachineBuilder.builder();
			b.configureConfiguration().withConfiguration().autoStartup(true).taskExecutor(new SyncTaskExecutor());
			b.configureStates().withStates().states(collectS).initial(from);
			//
			for (Transition<S, E> t : machine.getTransitions()) {
				b.configureTransitions().withExternal().source(t.getSource().getId()).target(t.getTarget().getId())
						.event(t.getTrigger().getEvent());
			}
			//
			StateMachine<S, E> copy = b.build();
			copy.start();
			copy.sendEvent(e.getTrigger().getEvent());
			//
			if (!Objects.equal(copy.getState().getId(), from)) {
				founded.add(copy.getState().getId());
			}
			copy.stop();
		}
		return founded;
	}

	public static class StateResult<S> {
		Collection<S> near = new LinkedHashSet<S>();
		Collection<S> far = new LinkedHashSet<S>();

		public Collection<S> getFar() {
			return far;
		}

		public Collection<S> getNear() {
			return near;
		}

		public void setFar(Collection<S> far) {
			this.far = far;
		}

		public void setNear(Collection<S> near) {
			this.near = near;
		}

		public int size() {
			return this.near.size() + this.far.size();
		}

		public void near(S id) {
			this.near.add(id);
			this.far.remove(id);
		}

		public void far(S id) {
			if (!this.near.contains(id)) {
				this.far.add(id);
			}
		}
	}

	public static <S, E> StateResult<S> findAllNextStatesRecursive(StateMachine<S, E> machine, S from)
			throws Exception {
		Set<S> collectS = new HashSet<S>();
		for (State<S, E> s : machine.getStates()) {
			collectS.add(s.getId());
		}
		//
		StateResult<S> founded = new StateResult<S>();
		for (Transition<S, E> e : machine.getTransitions()) {
			Builder<S, E> b = StateMachineBuilder.builder();
			b.configureConfiguration().withConfiguration().autoStartup(true).taskExecutor(new SyncTaskExecutor());
			b.configureStates().withStates().states(collectS).initial(from);
			//
			for (Transition<S, E> t : machine.getTransitions()) {
				b.configureTransitions().withExternal().source(t.getSource().getId()).target(t.getTarget().getId())
						.event(t.getTrigger().getEvent());
			}
			//
			StateMachine<S, E> copy = b.build();
			copy.start();
			boolean changed = false;
			int cpt = 0;
			do {
				S before = copy.getState().getId();
				copy.sendEvent(e.getTrigger().getEvent());
				//
				S after = copy.getState().getId();
				changed = !Objects.equal(after, before);
				//
				if (!Objects.equal(copy.getState().getId(), from)) {
					if (cpt == 0) {
						founded.near(copy.getState().getId());
					} else {
						founded.far(copy.getState().getId());
					}
				}
				cpt++;
			} while (changed);
			copy.stop();
		}
		return founded;
	}
}
