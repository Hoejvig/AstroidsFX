package commonbullet;

import servicelocator.ServiceLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public final class BulletServiceLocator {

    private BulletServiceLocator() {
    }

    public static List<BulletSPI> locateAll() {
        ServiceLoader<BulletSPI> loader =
                ServiceLoader.load(ServiceLocator.INSTANCE.getLayer(), BulletSPI.class);

        List<BulletSPI> bulletServices = new ArrayList<>();

        try {
            for (BulletSPI bulletSPI : loader) {
                bulletServices.add(bulletSPI);
            }
        } catch (ServiceConfigurationError error) {
            error.printStackTrace();
        }

        return bulletServices;
    }
}