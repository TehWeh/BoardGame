package data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface Log<T>{
   void addItem(T item);

   Collection<T> getNewest(int n);
   Collection<T> getOldest(int n);
   int getSize();
   void reset();
}
