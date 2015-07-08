package com.example.vaadin_test.views;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.model.ModelEntity;
import org.ofbiz.entity.model.ModelField;
import org.ofbiz.entity.model.ModelReader;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import com.example.vaadin_test.OfbizComponent;

public class EntityView extends OfbizComponent implements View {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private CssLayout mainLayout;
	@AutoGenerated
	private HorizontalSplitPanel mainSplitPanel;
	@AutoGenerated
	private VerticalLayout entityInfoLayout;
	@AutoGenerated
	private Table entityInfoTable;
	@AutoGenerated
	private Label entityNameLabel;
	@AutoGenerated
	private VerticalLayout entityListLayout;
	@AutoGenerated
	private Grid entityListGrid;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public EntityView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		initMainSplitPanel();
		initEntityListGrid();
		initEntityInfoTable();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
	private HorizontalSplitPanel initMainSplitPanel() {
		mainSplitPanel.setSplitPosition(25.0f, false);
		return mainSplitPanel;
	}
	
	private Grid initEntityListGrid() {
		final IndexedContainer entityListContainer = new IndexedContainer();
		entityListContainer.addContainerProperty("Name", String.class, null);
		
		ModelReader modelReader = delegator.getModelReader();
		Map<String, TreeSet<String>> entities = null;
		try {
			entities = modelReader.getEntitiesByPackage(null, null);
		}
		catch (GenericEntityException e) {
			Debug.log(e.getMessage());
			return entityListGrid;
		}
		
		for (Entry<String, TreeSet<String>> entry : entities.entrySet()) {
			for (String entityName : entry.getValue()) {
				Item newItem = entityListContainer.getItem(entityListContainer.addItem());
				newItem.getItemProperty("Name").setValue(entityName);
			}
		}
		
		entityListGrid.setCaption("Entity List");
		entityListGrid.setContainerDataSource(entityListContainer);
		
		// add grid item click listener
	    entityListGrid.addItemClickListener(new ItemClickEvent.ItemClickListener() {
	        @Override
	        public void itemClick(ItemClickEvent itemClickEvent) {
	            String entityName = itemClickEvent.getItem().getItemProperty("Name").getValue().toString();
	            ModelEntity entityModel = delegator.getModelEntity(entityName);
	            entityNameLabel.setValue(entityModel.getEntityName());
	            
	            entityInfoTable.getContainerDataSource().removeAllItems();
	            int fieldIndex = 0;
	            for (ModelField field : entityModel.getFieldsUnmodifiable()) {
	            	entityInfoTable.addItem(
	            		new Object[]{
	            			field.getName(),
	            			field.getType()
	            			// delegator.getEntityFieldType(entityModel, field.getType()).getJavaType()
	            		}, fieldIndex++
	            	);
	            }
	        }
	    });
	    
	    // add service list filter
		HeaderRow filterRow = entityListGrid.appendHeaderRow();
		HeaderCell cell = filterRow.getCell("Name");
		
	    TextField filterField = new TextField();
	    
	    filterField.addTextChangeListener(new TextChangeListener() {
			@Override
			public void textChange(TextChangeEvent event) {
				entityListContainer.removeContainerFilters("Name");
				if (! event.getText().isEmpty()) {
					entityListContainer.addContainerFilter(
						new SimpleStringFilter("Name", event.getText(), true, false)
					);
				}
			}
	    });

	    cell.setComponent(filterField);	
	    
		return entityListGrid;
	}
	
	private Table initEntityInfoTable() {
		entityInfoTable.addContainerProperty("Name", String.class, null);
		entityInfoTable.addContainerProperty("Type", String.class, null);
		// entityInfoTable.addContainerProperty("Java Type", String.class, null);
		return entityInfoTable;
	}

	@AutoGenerated
	private CssLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new CssLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// mainSplitPanel
		mainSplitPanel = buildMainSplitPanel();
		mainLayout.addComponent(mainSplitPanel);
		
		return mainLayout;
	}

	@AutoGenerated
	private HorizontalSplitPanel buildMainSplitPanel() {
		// common part: create layout
		mainSplitPanel = new HorizontalSplitPanel();
		mainSplitPanel.setImmediate(false);
		mainSplitPanel.setWidth("100.0%");
		mainSplitPanel.setHeight("100.0%");
		
		// entityListLayout
		entityListLayout = buildEntityListLayout();
		mainSplitPanel.addComponent(entityListLayout);
		
		// entityInfoLayout
		entityInfoLayout = buildEntityInfoLayout();
		mainSplitPanel.addComponent(entityInfoLayout);
		
		return mainSplitPanel;
	}

	@AutoGenerated
	private VerticalLayout buildEntityListLayout() {
		// common part: create layout
		entityListLayout = new VerticalLayout();
		entityListLayout.setImmediate(false);
		entityListLayout.setWidth("100.0%");
		entityListLayout.setHeight("100.0%");
		entityListLayout.setMargin(false);
		
		// entityListGrid
		entityListGrid = new Grid();
		entityListGrid.setImmediate(false);
		entityListGrid.setWidth("100.0%");
		entityListGrid.setHeight("100.0%");
		entityListLayout.addComponent(entityListGrid);
		
		return entityListLayout;
	}

	@AutoGenerated
	private VerticalLayout buildEntityInfoLayout() {
		// common part: create layout
		entityInfoLayout = new VerticalLayout();
		entityInfoLayout.setImmediate(false);
		entityInfoLayout.setWidth("100.0%");
		entityInfoLayout.setHeight("100.0%");
		entityInfoLayout.setMargin(false);
		
		// entityNameLabel
		entityNameLabel = new Label();
		entityNameLabel.setImmediate(false);
		entityNameLabel.setWidth("-1px");
		entityNameLabel.setHeight("-1px");
		entityNameLabel.setValue("Label");
		entityInfoLayout.addComponent(entityNameLabel);
		
		// entityInfoTable
		entityInfoTable = new Table();
		entityInfoTable.setImmediate(false);
		entityInfoTable.setWidth("100.0%");
		entityInfoTable.setHeight("-1px");
		entityInfoLayout.addComponent(entityInfoTable);
		
		return entityInfoLayout;
	}
}