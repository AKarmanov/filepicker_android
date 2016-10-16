package com.filepicker_android.filepicker.contextual;

import android.util.Log;

import com.filepicker_android.filepicker.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author alexander karmanov on 2016-10-10.
 */

public class FilepickerFilter {

    public static final String SIZE = "size";
    public static final String TYPE = "type";
    public static final String DATE = "date";
    public static final String NAME = "name";
    public static final String SORT_TYPE_ASC = "ascending";
    public static final String SORT_TYPE_DESC = "descending";

    public static final String FILTER_LAYOUT = "layoutFiler";
    public static final String FILTER_SORT = "sortFiler";

    public static Map<String, FilterSetting[]> sortOption;
    static {
        sortOption = new HashMap<>();
        sortOption.put(SIZE, new FilterSetting[]{
                new FilterSetting(SIZE, SORT_TYPE_ASC, false, "SIZE", R.string.icon_sort_amount_asc),
                new FilterSetting(SIZE, SORT_TYPE_DESC, false, "SIZE", R.string.icon_sort_amount_desc)
        });
        sortOption.put(TYPE, new FilterSetting[]{
                new FilterSetting(TYPE, SORT_TYPE_ASC, false, "TYPE", R.string.icon_sort_asc),
                new FilterSetting(TYPE, SORT_TYPE_DESC, false, "TYPE", R.string.icon_sort_desc)
        });
        sortOption.put(DATE, new FilterSetting[]{
                new FilterSetting(DATE, SORT_TYPE_ASC, false, "DATE", R.string.icon_sort_num_asc),
                new FilterSetting(DATE, SORT_TYPE_DESC, false, "DATE", R.string.icon_sort_num_desc)
        });
        sortOption.put(NAME, new FilterSetting[]{
                new FilterSetting(NAME, SORT_TYPE_ASC, false, "NAME", R.string.icon_sort_alpha_asc),
                new FilterSetting(NAME, SORT_TYPE_DESC, true, "NAME", R.string.icon_sort_alpha_desc)
        });
    }

    public static final String GRID = "grid";
    public static final String LIST = "list";
    public static final String LAYOUT_TYPE_SIMPLE = "layoutSimple";
    public static final String LAYOUT_TYPE_DETAILED = "layoutDetailed";


    public static Map<String, FilterSetting[]> layoutOption;
    static {
        layoutOption = new HashMap<>();
        layoutOption.put(GRID, new FilterSetting[]{
                new FilterSetting(GRID, LAYOUT_TYPE_DETAILED, false, "GRID DETAILED", R.string.icon_grid),
                new FilterSetting(GRID, LAYOUT_TYPE_SIMPLE, false, "GRID SIMPLE", R.string.icon_grid)
        });
        layoutOption.put(LIST, new FilterSetting[]{
                new FilterSetting(LIST, LAYOUT_TYPE_DETAILED, true, "LIST DETAILED", R.string.icon_list_ul),
                new FilterSetting(LIST, LAYOUT_TYPE_SIMPLE, false, "LIST SIMPLE", R.string.icon_bars)
        });
    }

    public static void setSortOption(String option, String sortType) {
        setOption(sortOption, option, sortType);
    }

    public static FilterSetting getSortOption() {
        return getOption(sortOption);
    }

    public static void setLayoutOption(String option, String sortType) {
        setOption(layoutOption, option, sortType);
    }

    public static FilterSetting getLayoutOption() {
        return getOption(layoutOption);
    }

    private static void setOption(Map<String, FilterSetting[]> map, String option, String sortType) {
        clearOptions(map);
        FilterSetting[] f = map.get(option);
        for (FilterSetting aF : f) {
            if (aF.getType().equals(sortType)) {
                aF.set();
            }
        }
    }

    private static FilterSetting getOption(Map<String, FilterSetting[]> map) {
        Set<String> keys = map.keySet();
        for (String key : keys) {
            FilterSetting[] f = map.get(key);
            for (FilterSetting aF : f) {
                if (aF.isSet()) {
                    return aF;
                }
            }
        }
        return null;
    }

    private static void clearOptions(Map<String, FilterSetting[]> map) {
        Set<String> keys = map.keySet();
        for (String key : keys) {
            FilterSetting[] f = map.get(key);
            for (FilterSetting aF : f) {
                if (aF.isSet()) {
                    aF.unset();
                }
            }
        }
    }

    public static class FilterSetting {
        private String option;
        private String type;
        private String text;
        private boolean set;
        private int icon;

        public FilterSetting(String option, String type, boolean set, String text, int icon) {
            this.option = option;
            this.type = type;
            this.set = set;
            this.text = text;
            this.icon = icon;
        }

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isSet() {
            return set;
        }

        public void unset() {
            set = false;
        }

        public void set() {
            set = true;
        }

        public String getText() {
            return text;
        }

        public int getIcon() {
            return icon;
        }
    }

    public static void sort(List<FilepickerFile> files) {
        FilepickerFilter.FilterSetting setting = FilepickerFilter.getSortOption();
        switch(setting.getOption()) {
            case FilepickerFilter.SIZE :
                if (setting.getType().equals(FilepickerFilter.SORT_TYPE_DESC)) {
                    Collections.sort(files, new SizeSort(FilepickerFilter.SORT_TYPE_DESC));
                }
                else {
                    Collections.sort(files, new SizeSort(FilepickerFilter.SORT_TYPE_ASC));
                }
                break;
            case FilepickerFilter.NAME :
                if (setting.getType().equals(FilepickerFilter.SORT_TYPE_DESC)) {
                    Collections.sort(files, new NameSort(FilepickerFilter.SORT_TYPE_DESC));
                }
                else {
                    Collections.sort(files, new NameSort(FilepickerFilter.SORT_TYPE_ASC));
                }
                break;
            case FilepickerFilter.DATE :
                if (setting.getType().equals(FilepickerFilter.SORT_TYPE_DESC)) {
                    Collections.sort(files, new DateSort(FilepickerFilter.SORT_TYPE_DESC));
                }
                else {
                    Collections.sort(files, new DateSort(FilepickerFilter.SORT_TYPE_ASC));
                }
                break;
            case FilepickerFilter.TYPE :
                if (setting.getType().equals(FilepickerFilter.SORT_TYPE_DESC)) {
                    Collections.sort(files, new TypeSort(FilepickerFilter.SORT_TYPE_DESC));
                }
                else {
                    Collections.sort(files, new TypeSort(FilepickerFilter.SORT_TYPE_ASC));
                }
                break;

        }
    }

    private static SizeSort sizeSortInstance(String sortType) {
        return new SizeSort(sortType);
    }

    private static class SizeSort implements Comparator<FilepickerFile> {
        private String type;

        public SizeSort(String type) {
            this.type = type;
        }

        @Override
        public int compare(FilepickerFile o1, FilepickerFile o2) {
            switch (type) {
                case FilepickerFilter.SORT_TYPE_ASC :
                    return (int) (o1.getSize() - o2.getSize());
                case FilepickerFilter.SORT_TYPE_DESC :
                    return (int) (o2.getSize() - o1.getSize());
                default:
                    return (int) (o2.getSize() - o1.getSize());
            }
        }
    }

    private static class NameSort implements Comparator<FilepickerFile> {
        private String type;

        public NameSort(String type) {
            this.type = type;
        }

        @Override
        public int compare(FilepickerFile o1, FilepickerFile o2) {
            switch (type) {
                case FilepickerFilter.SORT_TYPE_ASC :
                    return o1.getName().compareTo(o2.getName());
                case FilepickerFilter.SORT_TYPE_DESC :
                    return o2.getName().compareTo(o1.getName());
                default:
                    return o1.getName().compareTo(o2.getName());
            }
        }
    }

    private static class DateSort implements Comparator<FilepickerFile> {
        private String type;

        public DateSort(String type) {
            this.type = type;
        }

        @Override
        public int compare(FilepickerFile o1, FilepickerFile o2) {
            switch (type) {
                case FilepickerFilter.SORT_TYPE_ASC :
                    return (int) (o1.getLastModified() - o2.getLastModified());
                case FilepickerFilter.SORT_TYPE_DESC :
                    return (int) (o2.getLastModified() - o1.getLastModified());
                default:
                    return (int) (o1.getLastModified() - o2.getLastModified());
            }
        }
    }

    private static class TypeSort implements Comparator<FilepickerFile> {
        private String type;

        public TypeSort(String type) {
            this.type = type;
        }

        @Override
        public int compare(FilepickerFile o1, FilepickerFile o2) {
            if (o1.getType() == null || o2.getType() == null) {
                return 0;
            }
            switch (type) {
                case FilepickerFilter.SORT_TYPE_ASC :
                    return o1.getType().compareTo(o2.getType());
                case FilepickerFilter.SORT_TYPE_DESC :
                    return o2.getType().compareTo(o1.getType());
                default:
                    return o1.getType().compareTo(o2.getType());
            }
        }
    }
}
