import { myRxStompConfig } from "./rx-stomp.config";
import { RxStompService } from "./rxstomp.service";


export function rxStompServiceFactory() {
  const rxStomp = new RxStompService();
  rxStomp.configure(myRxStompConfig);
  rxStomp.activate();
  return rxStomp;
}
