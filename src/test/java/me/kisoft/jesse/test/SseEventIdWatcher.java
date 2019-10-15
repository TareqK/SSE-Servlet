/*
 * Copyright 2019 tareq.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.kisoft.jesse.test;

import javax.ws.rs.sse.InboundSseEvent;
import me.kisoft.jesse.SseEvent;

/**
 *
 * @author tareq
 */
public class SseEventIdWatcher extends SseEventWatcher<SseEvent> {

  public SseEventIdWatcher(SseEvent event) {
    super(event);
  }

  @Override
  public boolean matches(InboundSseEvent event, SseEvent toWatch) {
    return (event.getId() == null ? toWatch.getId() == null : event.getId().equals(toWatch.getId()));
  }

}
