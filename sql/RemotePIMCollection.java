package sql;


import java.util.Date;

import five.PIMCollection;
import five.PIMEntity;

interface RemotePIMCollection {
  public PIMCollection getNotes();
  public PIMCollection getNotes(String owner);
  public PIMCollection getTodos();
  public PIMCollection getTodos(String owner);
  public PIMCollection getAppointments();
  public PIMCollection getAppointments(String owner);
  public PIMCollection getContacts();
  public PIMCollection getContacts(String owner);
  public PIMCollection getItemsForDate(Date d);
  public PIMCollection getItemsForDate(Date d, String owner);
  public PIMCollection getAll();
  public PIMCollection getAllByOwner(String owner);
  public boolean add(PIMEntity pimEntity);
}