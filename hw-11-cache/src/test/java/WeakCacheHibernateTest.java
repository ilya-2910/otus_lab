import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import ru.otus.cachehw.CacheInterceptor;
import ru.otus.core.dao.EntityDao;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.model.PhoneDataSet;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;

@Slf4j
public class WeakCacheHibernateTest {

    @Test
    public void test() {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                User.class, AddressDataSet.class, PhoneDataSet.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        EntityDao<User> userDao = new UserDaoHibernate(sessionManager);

        DBServiceUser dbServiceUser = CacheInterceptor.getProxy(new DbServiceUserImpl(userDao), DBServiceUser.class);


        User вася = new User(0, "Вася");
        AddressDataSet addressDataSet = new AddressDataSet(0l, "пушкина 41");
        addressDataSet.setUser(вася);
        вася.setAddressDataSet(addressDataSet);
        ArrayList<PhoneDataSet> list = new ArrayList<>();
        list.add(new PhoneDataSet(вася, null,"2-12-86-06"));
        list.add(new PhoneDataSet(вася,null, "2-12-86-07"));
        вася.setPhoneDataSet(list);
        long id = dbServiceUser.saveUser(вася);

        System.gc();
        dbServiceUser.getUser(id);
        System.gc();
        dbServiceUser.getUser(id);
        System.gc();
        dbServiceUser.getUser(id);
        System.gc();
        dbServiceUser.getUser(id);
        System.gc();
        dbServiceUser.getUser(id);

    }

}
